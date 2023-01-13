package omurzakov.zerowaste.communication

import omurzakov.zerowaste.architecture.CommunicationResult
import omurzakov.zerowaste.architecture.IBaseRemoteRepository
import omurzakov.zerowaste.models.Can
import omurzakov.zerowaste.models.WasteType

interface IRemoteRepository : IBaseRemoteRepository {
    suspend fun getContainers(): CommunicationResult<List<Can>>
    suspend fun getContainersBy(category: String): CommunicationResult<List<Can>>
    suspend fun getWasteTypeInfo(category: String): CommunicationResult<WasteType>
}