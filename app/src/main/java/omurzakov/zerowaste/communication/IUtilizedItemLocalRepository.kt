package omurzakov.zerowaste.communication

import androidx.lifecycle.LiveData
import omurzakov.zerowaste.models.UtilizedItem

interface IUtilizedItemLocalRepository {
    fun getAll(): LiveData<MutableList<UtilizedItem>>
    suspend fun findById(id: Long): UtilizedItem
    fun insertUtilizedItem(utilizedItem: UtilizedItem): Long
}