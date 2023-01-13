package omurzakov.zerowaste.communication

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import omurzakov.zerowaste.models.UtilizedItem
import omurzakov.zerowaste.utils.Constant
import omurzakov.zerowaste.utils.Constant.UT_ITEM
import java.time.LocalDateTime
import java.time.ZoneOffset

class MockLocalRepositoryImpl : IUtilizedItemLocalRepository {

    private var localDB = mutableStateListOf(
        UtilizedItem(
            1,
            name = "Clothes",
            image = Constant.TEST_IMAGE,
            number = 21,
            createdDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        ), UtilizedItem(
            2,
            name = "Paper",
            image = Constant.TEST_IMAGE,
            number = 12,
            createdDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        )
    )

    override fun getAll(): LiveData<MutableList<UtilizedItem>> {
        return MutableLiveData(localDB)
    }

    override suspend fun findById(id: Long): UtilizedItem = UT_ITEM

    override fun insertUtilizedItem(utilizedItem: UtilizedItem): Long {
        utilizedItem.id = localDB.size + 1L
        localDB.add(utilizedItem)

        return localDB.size + 1L
    }
}