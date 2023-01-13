package omurzakov.zerowaste.ui.screens.historydetail

sealed class HistoryDetailUiState<out T> {
    object Start : HistoryDetailUiState<Nothing>()
    object Loading : HistoryDetailUiState<Nothing>()
    class Success<T>(var data: T) : HistoryDetailUiState<T>()
    class Error(var error: Int) : HistoryDetailUiState<Nothing>()
}