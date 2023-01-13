package omurzakov.zerowaste.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import omurzakov.zerowaste.R
import omurzakov.zerowaste.models.UtilizedItem
import omurzakov.zerowaste.navigation.INavigationRouter
import omurzakov.zerowaste.ui.elements.SortNowDialog
import omurzakov.zerowaste.ui.theme.GreenyGreen
import omurzakov.zerowaste.utils.Constant.TT_HISTORY_LIST
import omurzakov.zerowaste.utils.Constant.TT_SORT_NOW_BUTTON
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigation: INavigationRouter, viewModel: HomeViewModel = getViewModel()) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            })
        },
        content = {
            HomeScreenDetail(paddingValues = it, navigation = navigation, viewModel = viewModel)
        },
    )
}

@Composable
fun HomeScreenDetail(
    paddingValues: PaddingValues, navigation: INavigationRouter, viewModel: HomeViewModel
) {
    val openDialog = remember { mutableStateOf(false) }
    val utilizedItems = viewModel.getUtilizedItems().observeAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
    ) {
        Image(
            painterResource(R.drawable.save),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(200.dp)
        )

        Text(
            text = stringResource(R.string.save_the_planet),
            fontSize = 26.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )

        LinearProgressIndicator(
            progress = 1f,
            color = GreenyGreen,
            trackColor = Color(0xFFF0C426),
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(30.dp)
        )

        Button(
            onClick = { openDialog.value = true },
            shape = RoundedCornerShape(40),
            colors = ButtonDefaults.buttonColors(GreenyGreen),
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .testTag(TT_SORT_NOW_BUTTON),
        ) {
            Text(
                text = stringResource(R.string.sort_now),
                fontSize = 20.sp,
            )
        }

        if (openDialog.value) {
            SortNowDialog(openDialog, navigation)
        }

        Text(
            text = stringResource(R.string.last_actions),
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .padding(start = 10.dp)
                .padding(top = 20.dp)
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.testTag(TT_HISTORY_LIST)
        ) {
            utilizedItems.value?.forEach {
                item(key = it.id) {
                    HistoryRow(item = it, rowValue = it.name, onRowClick = {
                        navigation.navigateToHistory(it.id!!)
                    })
                }
            }
        }
    }
}

@Composable
fun HistoryRow(item: UtilizedItem, rowValue: String, onRowClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 6.dp)
            .border(1.dp, GreenyGreen, shape = MaterialTheme.shapes.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
                .clickable(onClick = onRowClick)
        ) {
            Icon(
                painter = painterResource(R.drawable.trash),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp),
                tint = GreenyGreen
            )

            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(top = 13.dp),
                    overflow = TextOverflow.Ellipsis,
                    text = rowValue
                )

                Text(
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(bottom = 13.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light,
                    overflow = TextOverflow.Ellipsis,
                    text = LocalDateTime.ofEpochSecond(item.createdDate, 0, ZoneOffset.UTC)
                        .toString()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painterResource(R.drawable.arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 2.dp)
            )
        }
    }
}