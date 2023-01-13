package omurzakov.zerowaste.ui.screens.historydetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import omurzakov.zerowaste.architecture.BaseViewModel
import omurzakov.zerowaste.communication.IUtilizedItemLocalRepository
import omurzakov.zerowaste.models.UtilizedItem

class HistoryDetailViewModel(private val repo: IUtilizedItemLocalRepository) : BaseViewModel() {

    val uiState: MutableState<HistoryDetailUiState<UtilizedItem>> =
        mutableStateOf(HistoryDetailUiState.Start)

    fun getItem(id: Long) {
        launch {
            uiState.value =
                withContext(Dispatchers.IO) { HistoryDetailUiState.Success(repo.findById(id)) }
        }
    }
}