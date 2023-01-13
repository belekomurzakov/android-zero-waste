package omurzakov.zerowaste.communication

import omurzakov.zerowaste.architecture.CommunicationResult
import omurzakov.zerowaste.models.Can
import omurzakov.zerowaste.models.WasteType
import omurzakov.zerowaste.utils.Constant
import omurzakov.zerowaste.utils.Constant.CANS_CLOTHES
import omurzakov.zerowaste.utils.Constant.CANS_DIFF

class MockRemoteRepositoryImpl : IRemoteRepository {

    override suspend fun getContainers(): CommunicationResult<List<Can>> {
        return CommunicationResult.Success(CANS_DIFF)
    }

    override suspend fun getContainersBy(category: String): CommunicationResult<List<Can>> {
        return CommunicationResult.Success(CANS_CLOTHES)
    }

    override suspend fun getWasteTypeInfo(category: String): CommunicationResult<WasteType> {
        return CommunicationResult.Success(
            WasteType(
                imageUrl = Constant.TEST_IMAGE,
                decompositionTime = "1 to 2",
                description = "Test Description",
                kills = 200,
                cans = 100
            )
        )
    }
}