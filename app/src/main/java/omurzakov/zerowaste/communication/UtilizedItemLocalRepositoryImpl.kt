package omurzakov.zerowaste.communication

import androidx.lifecycle.LiveData
import omurzakov.zerowaste.models.UtilizedItem

class UtilizedItemLocalRepositoryImpl(private val utilizedItemDao: UtilizedItemDao) :
    IUtilizedItemLocalRepository {

    override fun getAll(): LiveData<MutableList<UtilizedItem>> {
        return utilizedItemDao.getAll()
    }

    override suspend fun findById(id: Long): UtilizedItem {
        return utilizedItemDao.findById(id)
    }

    override fun insertUtilizedItem(utilizedItem: UtilizedItem): Long {
        return utilizedItemDao.insertUtilizedItem(utilizedItem)
    }
}