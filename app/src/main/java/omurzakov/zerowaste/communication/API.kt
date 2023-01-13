package omurzakov.zerowaste.communication

import omurzakov.zerowaste.models.Can
import omurzakov.zerowaste.models.WasteType
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @Headers("Content-Type: application/json")
    @GET("cans/all")
    suspend fun getContainers(): Response<List<Can>>

    @Headers("Content-Type: application/json")
    @GET("cans")
    suspend fun getContainersBy(@Query("type") category: String): Response<List<Can>>

    @Headers("Content-Type: application/json")
    @GET("waste_type/{id}")
    suspend fun getWasteTypeInfo(@Path("id") id: String): Response<WasteType>
}