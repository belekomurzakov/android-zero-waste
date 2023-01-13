package omurzakov.zerowaste.ui.screens.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import omurzakov.zerowaste.R
import omurzakov.zerowaste.architecture.BaseViewModel
import omurzakov.zerowaste.architecture.CommunicationResult
import omurzakov.zerowaste.communication.IRemoteRepository
import omurzakov.zerowaste.communication.IUtilizedItemLocalRepository
import omurzakov.zerowaste.models.UtilizedItem
import omurzakov.zerowaste.models.WasteType
import java.time.LocalDateTime.now
import java.time.ZoneOffset

class DetailViewModel(
    private val remoteRepo: IRemoteRepository,
    private val localRepo: IUtilizedItemLocalRepository
) : BaseViewModel() {

    val detailUiState: MutableState<DetailUiState<WasteType>> = mutableStateOf(DetailUiState.Start)
    var wasteType: WasteType? = null

    fun createHistoryRecord(category: String, number: Int) {
        localRepo.insertUtilizedItem(
            UtilizedItem(
                name = category,
                number = number,
                image = wasteType?.imageUrl!!,
                createdDate = now().toEpochSecond(ZoneOffset.UTC)
            )
        )
    }

    fun alignCategory(category: String): String {
        return when (category) {
            "Cans" -> "Plastic, beverage cartons and cans"
            "Plastic" -> "Plastic, beverage cartons and cans"
            "Beverage cartons" -> "Plastic, beverage cartons and cans"
            else -> category
        }
    }

    fun loadData(category: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                remoteRepo.getWasteTypeInfo(category)
            }

            when (result) {
                is CommunicationResult.Error -> {
                    detailUiState.value = DetailUiState.Error(R.string.failed_item)
                }
                is CommunicationResult.Exception -> {
                    detailUiState.value = DetailUiState.Error(R.string.no_internet_connection)
                }
                is CommunicationResult.Success -> {
                    detailUiState.value = DetailUiState.Success(result.data)
                }
            }
        }
    }
}