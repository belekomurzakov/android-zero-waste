package omurzakov.zerowaste.ui.screens.category

sealed class CategoryUiState<out T> {
    object Start : CategoryUiState<Nothing>()
    class Success<T>(var data: T) : CategoryUiState<T>()
    class Error(var error: Int) : CategoryUiState<Nothing>()
}