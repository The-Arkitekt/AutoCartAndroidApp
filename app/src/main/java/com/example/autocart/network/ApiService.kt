package com.example.autocart.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AutocartApiService {
    /**
    POST a command to the RobotMover service
     */
    @FormUrlEncoded
    @POST("command")
    suspend fun postMoveCommand(@Field("magnitude")magnitude: String,
                                @Field("direction")direction: String): Response<ResponseBody>
}
