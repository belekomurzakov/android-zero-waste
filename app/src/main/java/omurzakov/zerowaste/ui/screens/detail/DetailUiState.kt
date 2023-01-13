package omurzakov.zerowaste.ui.screens.detail

sealed class DetailUiState<out T> {
    object Start : DetailUiState<Nothing>()
    object Loading : DetailUiState<Nothing>()
    class Success<T>(var data: T) : DetailUiState<T>()
    class Error(var error: Int) : DetailUiState<Nothing>()
}