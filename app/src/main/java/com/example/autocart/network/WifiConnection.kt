package com.example.autocart.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiNetworkSpecifier
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.autocart.model.NetworkConfiguration

class WifiConnection(context: Context) {
    var connected: Boolean = false
    private val context: Context = context


    fun connectToNetwork(networkConfiguration: NetworkConfiguration) {
        /**
         * Cinfigure desired network coneection
         */
        val wifiNetworkSpecifier: WifiNetworkSpecifier = WifiNetworkSpecifier.Builder()
            .setSsid(networkConfiguration.ssid)
            .setWpa2Passphrase(networkConfiguration.key)
            .build()
        /**
         * Create network connection request
         */
        val networkRequest: NetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .setNetworkSpecifier(wifiNetworkSpecifier)
            .build()

        val connectivityManager: ConnectivityManager =
            this.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                println("on Available:")
                connected = connectivityManager.bindProcessToNetwork(network)
            }
        }
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}