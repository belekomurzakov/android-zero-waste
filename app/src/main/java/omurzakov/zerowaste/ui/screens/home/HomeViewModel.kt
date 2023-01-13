package omurzakov.zerowaste.ui.screens.home

import androidx.lifecycle.LiveData
import omurzakov.zerowaste.architecture.BaseViewModel
import omurzakov.zerowaste.communication.IUtilizedItemLocalRepository
import omurzakov.zerowaste.models.UtilizedItem

class HomeViewModel(private val repository: IUtilizedItemLocalRepository) : BaseViewModel() {

    fun getUtilizedItems(): LiveData<MutableList<UtilizedItem>> = repository.getAll()

}