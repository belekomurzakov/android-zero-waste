package omurzakov.zerowaste.communication

import androidx.lifecycle.LiveData
import androidx.room.*
import omurzakov.zerowaste.models.UtilizedItem

@Dao
interface UtilizedItemDao {

    @Query("SELECT * FROM UtilizedItem ORDER BY createdDate DESC")
    fun getAll(): LiveData<MutableList<UtilizedItem>>

    @Query("SELECT * FROM UtilizedItem WHERE id = :id")
    suspend fun findById(id: Long): UtilizedItem

    @Insert
    fun insertUtilizedItem(UtilizedItem: UtilizedItem): Long

    @Insert
    fun insertAll(UtilizedItemList: List<UtilizedItem>): List<Long>

    @Update
    suspend fun updateUtilizedItem(UtilizedItem: UtilizedItem)

    @Delete
    suspend fun deleteUtilizedItem(UtilizedItem: UtilizedItem)
}