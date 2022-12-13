package services

import services.response.StopInfo
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TflApi {
    @GET("/StopPoint/{id}/placeTypes")
    suspend fun getStopPoint(
        @Path("id") id: String,
        @Query("placeTypes") placeTypes: String,
    ): Response<ResponseBody>

    @GET("/StopPoint/{id}")
    suspend fun getStopInfo(
        @Path("id") id: String
    ): Response<StopInfo>
}