package omurzakov.zerowaste.ui.screens.camera

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import omurzakov.zerowaste.R
import omurzakov.zerowaste.navigation.INavigationRouter
import omurzakov.zerowaste.ui.elements.BackArrowBar
import omurzakov.zerowaste.ui.screens.category.CategoryRow
import omurzakov.zerowaste.ui.theme.GreenyGreen
import omurzakov.zerowaste.utils.Constant.TT_ANALYZE_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_DETECTED_OBJECTS
import omurzakov.zerowaste.utils.Constant.TT_PICK_PHOTO
import omurzakov.zerowaste.utils.bitmap
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GalleryScreen(navigation: INavigationRouter, viewModel: CameraViewModel = getViewModel()) {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = {
            BackArrowBar(
                topBarText = stringResource(R.string.back),
                onBackClick = { navigation.returnBack() },
                actions = {})
        },
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.background,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(20.dp),
        sheetContent = {
            IdentifiedCategorySheet(
                navigation = navigation,
                sheetState = sheetState,
                scope = scope,
                viewModel = viewModel
            )

        },
        content = {
            GalleryContent(
                sheetState = sheetState, scope = scope, viewModel = viewModel
            )
        })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GalleryContent(
    sheetState: BottomSheetState, scope: CoroutineScope, viewModel: CameraViewModel
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    val bitmap by bitmap(imageUri = imageUri)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .height(400.dp),
                contentScale = ContentScale.Inside
            )
        } else {
            Box(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(10),
                        color = Color.Gray.copy(alpha = 0.3f)
                    )
                    .size(400.dp)
            )
        }

        Row(
            Modifier.padding(vertical = 10.dp)
        ) {
            androidx.compose.material.OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .align(alignment = Alignment.CenterVertically)
                    .testTag(TT_PICK_PHOTO),
                shape = RoundedCornerShape(40),
                border = BorderStroke(2.dp, GreenyGreen),
                onClick = { launcher.launch("image/*") }
            ) {
                Text(text = stringResource(R.string.pick_photo))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                modifier = Modifier
                    .weight(1f)
                    .align(alignment = Alignment.CenterVertically)
                    .testTag(TT_ANALYZE_BUTTON),
                shape = RoundedCornerShape(40),
                colors = ButtonDefaults.buttonColors(GreenyGreen),
                onClick = {
                    if (bitmap != null) {
                        viewModel.getImageLabels(bitmap!!)
                        scope.launch { sheetState.expand() }
                    }
                }
            ) {
                Text(text = stringResource(R.string.analyze))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IdentifiedCategorySheet(
    navigation: INavigationRouter,
    sheetState: BottomSheetState,
    scope: CoroutineScope,
    viewModel: CameraViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp), elevation = 20.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(R.string.machine_identifies),
                    color = Color(0xFF777777),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 12.dp)
                )
            }

            LazyColumn(modifier = Modifier.testTag(TT_DETECTED_OBJECTS)) {
                viewModel.detectedObjects.forEach {
                    item(key = it.hashCode()) {
                        CategoryRow(rowValue = it.text.capitalize(), onRowClick = {
                            navigation.navigateToCategoryDetail(
                                category = viewModel.identifyCategory(it.text)
                            )
                        })
                    }
                }
            }
        }
    }
}
