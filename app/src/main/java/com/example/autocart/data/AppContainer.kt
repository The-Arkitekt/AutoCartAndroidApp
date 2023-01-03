package com.example.autocart.data

import android.content.Context
import com.example.autocart.model.NetworkConfiguration
import com.example.autocart.network.AutocartApiService
import com.example.autocart.network.WifiConnection
import retrofit2.Retrofit

/**
 Dependency Injection container at the application level.
 */
interface AppContainer {
    val robotMover: RobotMover
    val connection: WifiConnection
    var networkConfiguration: NetworkConfiguration
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(context: Context) : AppContainer {
    private val BASE_URL = "http://192.168.4.1:80/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: AutocartApiService by lazy {
        retrofit.create(AutocartApiService::class.java)
    }

    /**
     * DI implementation for Robot Mover
     */
    override val robotMover: RobotMover by lazy {
        NetworkRobotMover(retrofitService)
    }

    /**
     * DI implementation for WifiConnection
     */
    override val connection: WifiConnection by lazy {
        WifiConnection(context)
    }

    /**
     * DI implementation for network configuration
     */
    override var networkConfiguration = NetworkConfiguration("null", "null")

}
