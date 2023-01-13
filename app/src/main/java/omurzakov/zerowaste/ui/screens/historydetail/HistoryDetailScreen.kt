package omurzakov.zerowaste.ui.screens.historydetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import omurzakov.zerowaste.R
import omurzakov.zerowaste.models.UtilizedItem
import omurzakov.zerowaste.navigation.INavigationRouter
import omurzakov.zerowaste.ui.elements.BackArrowBar
import omurzakov.zerowaste.ui.elements.ErrorScreen
import omurzakov.zerowaste.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    navigation: INavigationRouter, viewModel: HistoryDetailViewModel = getViewModel(), id: Long
) {
    val screenState: MutableState<HistoryDetailUiState<UtilizedItem>> =
        mutableStateOf(HistoryDetailUiState.Start)

    viewModel.uiState.value.let {
        when (it) {
            is HistoryDetailUiState.Start -> LaunchedEffect(it) { viewModel.getItem(id) }
            is HistoryDetailUiState.Error -> screenState.value =
                HistoryDetailUiState.Error(it.error)
            is HistoryDetailUiState.Success -> screenState.value =
                HistoryDetailUiState.Success(it.data)
            is HistoryDetailUiState.Loading -> screenState.value = HistoryDetailUiState.Loading
        }
    }

    Scaffold(
        topBar = {
            BackArrowBar(
                topBarText = stringResource(R.string.back),
                onBackClick = { navigation.returnBack() })
        },
        content = {
            HistoryDetailContent(
                screenState = screenState.value, paddingValues = it
            )
        },
    )
}


@Composable
fun HistoryDetailContent(
    screenState: HistoryDetailUiState<UtilizedItem>,
    paddingValues: PaddingValues
) {
    screenState.let {
        when (it) {
            is HistoryDetailUiState.Success -> HistoryDetail(
                data = it.data, paddingVal = paddingValues
            )
            is HistoryDetailUiState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is HistoryDetailUiState.Loading -> LoadingScreen()
            else -> {}
        }
    }
}

@Composable
fun HistoryDetail(
    data: UtilizedItem, paddingVal: PaddingValues
) {
    val dateTime = LocalDateTime.ofEpochSecond(data.createdDate, 0, ZoneOffset.UTC).toString()

    Column(Modifier.padding(paddingValues = paddingVal)) {
        AsyncImage(
            model = data.image, contentDescription = "", modifier = Modifier.padding(bottom = 10.dp)
        )
        DetailTitle(data.name)
        DetailProperty(stringResource(R.string.number), data.number.toString())
        DetailProperty(stringResource(R.string.date_time), dateTime)
    }
}

@Composable
fun DetailTitle(value: String) {
    Column(modifier = Modifier.padding(start = 12.dp, end = 16.dp, bottom = 12.dp)) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DetailProperty(label: String, value: String) {
    Column(modifier = Modifier.padding(start = 12.dp, end = 16.dp, bottom = 12.dp)) {
        Divider()
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = value,
            modifier = Modifier.padding(top = 2.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }
}