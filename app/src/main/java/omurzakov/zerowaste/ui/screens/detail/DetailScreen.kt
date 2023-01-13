package omurzakov.zerowaste.ui.screens.detail

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import omurzakov.zerowaste.R
import omurzakov.zerowaste.models.WasteType
import omurzakov.zerowaste.navigation.INavigationRouter
import omurzakov.zerowaste.ui.elements.BackArrowBar
import omurzakov.zerowaste.ui.elements.ErrorScreen
import omurzakov.zerowaste.ui.elements.LoadingScreen
import omurzakov.zerowaste.ui.theme.GreenyGreen
import omurzakov.zerowaste.utils.Constant.TT_CANCEL_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_CONFIRM_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_INPUT_NUMBER
import omurzakov.zerowaste.utils.Constant.TT_MAP_DETAIL_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_SHEET_CARD
import omurzakov.zerowaste.utils.Constant.TT_SORT_IT_BUTTON
import omurzakov.zerowaste.utils.DataStoreManager
import org.koin.androidx.compose.getViewModel
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    navigation: INavigationRouter, viewModel: DetailViewModel = getViewModel(), category: String
) {
    val context = LocalContext.current
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()

    val screenState: MutableState<DetailUiState<WasteType>> = mutableStateOf(DetailUiState.Start)

    viewModel.detailUiState.value.let {
        when (it) {
            is DetailUiState.Start -> {
                LaunchedEffect(it) { viewModel.loadData(category.lowercase(Locale.getDefault())) }
            }
            is DetailUiState.Error -> screenState.value = DetailUiState.Error(it.error)
            is DetailUiState.Success -> {
                screenState.value = DetailUiState.Success(it.data)
                viewModel.wasteType = it.data
            }
            is DetailUiState.Loading -> screenState.value = DetailUiState.Loading
        }
    }

    BottomSheetScaffold(scaffoldState = scaffoldState, topBar = {
        BackArrowBar(
            topBarText = category,
            onBackClick = { navigation.returnBack() },
            actions = {
                IconButton(modifier = Modifier.testTag(TT_MAP_DETAIL_BUTTON),
                    onClick = { navigation.navigateToMap(viewModel.alignCategory(category)) }) {
                    Icon(
                        painterResource(id = R.drawable.ic_map),
                        tint = GreenyGreen,
                        contentDescription = "",
                    )
                }
            })
    }, sheetShape = RoundedCornerShape(20.dp), sheetContent = {
        SheetContent(
            navigation = navigation,
            sheetState = sheetState,
            scope = scope,
            viewModel = viewModel,
            category = category,
            context = context
        )
    }, sheetBackgroundColor = MaterialTheme.colors.background, sheetPeekHeight = 0.dp
    ) {
        DetailContent(screenState = screenState.value, sheetState = sheetState, scope = scope)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailContent(
    screenState: DetailUiState<WasteType>, sheetState: BottomSheetState, scope: CoroutineScope
) {
    screenState.let {
        when (it) {
            is DetailUiState.Success -> DetailScreen(data = it.data, sheetState, scope)
            is DetailUiState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is DetailUiState.Loading -> LoadingScreen()
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(data: WasteType, sheetState: BottomSheetState, scope: CoroutineScope) {

    val num = DataStoreManager(LocalContext.current).getNumber().collectAsState("")

    AsyncImage(model = data.imageUrl, contentDescription = "")

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        IconWithText(image = R.drawable.trash, text = data.cans.toString())

        Spacer(modifier = Modifier.width(50.dp))

        IconWithText(
            image = R.drawable.access_time, text = data.decompositionTime + " " + stringResource(
                R.string.years
            )
        )

        Spacer(modifier = Modifier.width(30.dp))

        IconWithText(image = R.drawable.die, text = data.kills.toString())
    }

    Text(
        text = data.description,
        fontSize = 13.sp,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 19.sp,
        modifier = Modifier
            .padding(bottom = 5.dp)
            .padding(horizontal = 14.dp)
    )
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = stringResource(R.string.last_time) + " ${num.value} " + stringResource(R.string.items),
            fontSize = 13.sp,
            fontFamily = FontFamily.SansSerif,
            lineHeight = 19.sp,
            color = GreenyGreen,
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(horizontal = 14.dp)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.testTag(TT_SORT_IT_BUTTON),
            onClick = {
                scope.launch {
                    if (sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }
            }, colors = ButtonDefaults.buttonColors(GreenyGreen)
        ) {
            Text(
                text = stringResource(R.string.sort_it), fontSize = 20.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContent(
    navigation: INavigationRouter,
    sheetState: BottomSheetState,
    scope: CoroutineScope,
    viewModel: DetailViewModel,
    category: String,
    context: Context
) {
    val dataStore = DataStoreManager(context)

    Card(
        modifier = Modifier
            .testTag(TT_SHEET_CARD)
            .fillMaxWidth()
            .height(200.dp), elevation = 20.dp
    ) {
        var text by remember { mutableStateOf(TextFieldValue("")) }

        Column {
            Divider(
                modifier = Modifier
                    .padding(horizontal = 170.dp)
                    .padding(top = 17.dp),
                color = Color.Black
            )

            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = text,
                onValueChange = { text = it },
                label = { Text(text = stringResource(R.string.enter_number)) },
                modifier = Modifier
                    .align(CenterHorizontally)
                    .background(MaterialTheme.colors.background)
                    .testTag(TT_INPUT_NUMBER)
                    .padding(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = GreenyGreen,
                    unfocusedIndicatorColor = GreenyGreen
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                OutlinedButton(
                    onClick = { scope.launch { sheetState.collapse() } },
                    shape = RoundedCornerShape(40),
                    border = BorderStroke(2.dp, GreenyGreen),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .testTag(TT_CANCEL_BUTTON),
                ) {
                    Text(
                        text = stringResource(R.string.cancel), fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        viewModel.createHistoryRecord(
                            number = text.text.toInt(),
                            category = category
                        )

                        scope.launch {
                            dataStore.saveNumber(text.text)
                            sheetState.collapse()
                        }.invokeOnCompletion {
                            text = TextFieldValue("")
                            navigation.navigateToHome()
                        }
                    },
                    shape = RoundedCornerShape(40),
                    colors = ButtonDefaults.buttonColors(GreenyGreen),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .testTag(TT_CONFIRM_BUTTON),
                ) {
                    Text(
                        text = stringResource(R.string.confirm), fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun IconWithText(image: Int, text: String) {
    Row {
        Image(
            painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 2.dp),
            colorFilter = ColorFilter.tint(Color.Gray)

        )

        Text(
            text = text,
            fontSize = 13.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 2.dp)
        )
    }
}