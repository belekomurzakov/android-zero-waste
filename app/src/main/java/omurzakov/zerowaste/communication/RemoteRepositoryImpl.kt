package omurzakov.zerowaste.communication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omurzakov.zerowaste.architecture.CommunicationError
import omurzakov.zerowaste.architecture.CommunicationResult
import omurzakov.zerowaste.models.Can
import omurzakov.zerowaste.models.WasteType
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RemoteRepositoryImpl(private val api: API) : IRemoteRepository {

    override suspend fun getContainers(): CommunicationResult<List<Can>> {
        return try {
            processResponse(withContext(Dispatchers.IO) { api.getContainers() })
        } catch (timeoutEx: SocketTimeoutException) {
            CommunicationResult.Error(CommunicationError(0, "Timeout"))
        } catch (unknownHostEx: UnknownHostException) {
            CommunicationResult.Error(CommunicationError(0, "Unknown host"))
        }
    }

    override suspend fun getContainersBy(category: String): CommunicationResult<List<Can>> {
        return try {
            processResponse(withContext(Dispatchers.IO) { api.getContainersBy(category) })
        } catch (timeoutEx: SocketTimeoutException) {
            CommunicationResult.Error(CommunicationError(0, "Timeout"))
        } catch (unknownHostEx: UnknownHostException) {
            CommunicationResult.Error(CommunicationError(0, "Unknown host"))
        }
    }

    override suspend fun getWasteTypeInfo(category: String): CommunicationResult<WasteType> {
        return try {
            processResponse(withContext(Dispatchers.IO) { api.getWasteTypeInfo(category) })
        } catch (timeoutEx: SocketTimeoutException) {
            CommunicationResult.Error(CommunicationError(0, "Timeout"))
        } catch (unknownHostEx: UnknownHostException) {
            CommunicationResult.Error(CommunicationError(0, "Unknown host"))
        }
    }
}