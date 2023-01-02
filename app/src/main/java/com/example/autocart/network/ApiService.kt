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
    suspend fun postMoveCommand(@Field("content")onOrOff: String): Response<ResponseBody>
}

//Everything below here will be moved
/*
private const val BASE_URL =
    "http://192.168.4.1:80"

private val retrofit = Retrofit.Builder()
    .baseUrl("$BASE_URL/")
    .build()

interface AutocartApiService {
    @FormUrlEncoded
    @POST("command")
    suspend fun postCommand(@Field("value")onOrOff: String): Response<ResponseBody>

}

object AutocartApi {
    val retrofitService : AutocartApiService by lazy {
        retrofit.create(AutocartApiService::class.java)
    }
}

class Http{
    fun setValue(setValue: String = "0"): Boolean{
        // Create JSON using JSONObject
        //val jsonObject = JSONObject()
        //jsonObject.put("value", setValue)

        // Convert JSONObject to String
        //val jsonObjectString = jsonObject.toString()
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        //val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        var ret = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Do the POST request and get response
                val response = AutocartApi.retrofitService.postMoveCommand(setValue)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d("Response: ", response.body().toString())
                    } else {
                        Log.e("RETROFIT_ERROR: ", response.code().toString())
                        ret = false
                    }
                }
            } catch (e : IOException) {
                Log.e("EXCEPTION: ", e.toString())
                ret = false
            }
        }
        return ret
    }
}
*/