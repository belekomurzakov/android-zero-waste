package omurzakov.zerowaste.ui.elements

import java.io.Serializable

sealed class ScreenState<out T> : Serializable {
    object Loading : ScreenState<Nothing>()
    class DataLoaded<T>(var data: T) : ScreenState<T>()
    class Error(var error: Int) : ScreenState<Nothing>()
}