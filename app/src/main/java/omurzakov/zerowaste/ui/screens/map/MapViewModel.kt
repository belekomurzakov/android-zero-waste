package omurzakov.zerowaste.ui.screens.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import omurzakov.zerowaste.R
import omurzakov.zerowaste.architecture.BaseViewModel
import omurzakov.zerowaste.architecture.CommunicationResult
import omurzakov.zerowaste.communication.IRemoteRepository
import omurzakov.zerowaste.models.Can

class MapViewModel(private val repository: IRemoteRepository) : BaseViewModel() {

    val mapUiState: MutableState<MapUiState<List<Can>>> =
        mutableStateOf(MapUiState.Start)

    var selectedPlace: Can? = null

    fun getData(category: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                if (category == "no-category") {
                    repository.getContainers()
                } else {
                    repository.getContainersBy(category)
                }
            }

            when (result) {
                is CommunicationResult.Error -> {
                    mapUiState.value = MapUiState.Error(R.string.failed_item)
                }
                is CommunicationResult.Exception -> {
                    mapUiState.value = MapUiState.Error(R.string.no_internet_connection)
                }
                is CommunicationResult.Success -> {
                    mapUiState.value = MapUiState.Success(result.data)
                }
            }
        }
    }
}