package omurzakov.zerowaste.ui.screens.map

sealed class MapUiState<out T> {
    object Start : MapUiState<Nothing>()
    class Success<T>(var data: T) : MapUiState<T>()
    class Error(var error: Int) : MapUiState<Nothing>()
}