package com.example.autocart.network

import android.content.Context
import android.net.*
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import com.example.autocart.model.NetworkConfiguration
import com.example.autocart.ui.screens.AutoCartConnectionState
import com.example.autocart.ui.screens.AutoCartViewModel

class WifiConnection(context: Context) {
    private lateinit var _configuration: NetworkConfiguration
    private var _connected: Boolean = false
    private val context: Context = context

    init {
        if (isWifiAdapterEnabled()) {
            val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkCapabilities: NetworkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                        as NetworkCapabilities

            val wifiInfo: WifiInfo = networkCapabilities.transportInfo as WifiInfo
            if (wifiInfo != null) {
                _connected = true
                _configuration = NetworkConfiguration(wifiInfo.ssid, "")
            }
            else {
                _connected = false
                _configuration = NetworkConfiguration("", "")
            }
        } else {
            _connected = false
            _configuration = NetworkConfiguration("", "")
        }
    }

    /**
     * Getters and Setters
     */
    fun configuration() : NetworkConfiguration {
        return _configuration
    }
    fun connected() : Boolean {
        return _connected
    }

    fun isWifiAdapterEnabled() : Boolean{
        val wifiManager: WifiManager =
            context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        return wifiManager.isWifiEnabled
    }

    fun connectToNetwork(networkConfiguration: NetworkConfiguration, viewModel: AutoCartViewModel) {
        /**
         * Configure desired network connection
         */
        // check for empty request
        if (networkConfiguration.ssid == "" && networkConfiguration.key == "") {
            return
        }
        /**
         * Update UI
         */
        viewModel.updateLiveSsid("")
        viewModel.updateLiveConnectionStatus(AutoCartConnectionState.Connecting)

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
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _connected = connectivityManager.bindProcessToNetwork(network)
                if (_connected) {
                    viewModel.updateLiveConnectionStatus(AutoCartConnectionState.Connected)
                    _configuration = networkConfiguration
                    viewModel.updateLiveSsid(_configuration.ssid)
                }
            }
            override fun onUnavailable() {
                super.onUnavailable()
                _connected = false
                viewModel.updateLiveConnectionStatus(AutoCartConnectionState.NotConnected)
                _configuration = networkConfiguration
                viewModel.updateLiveSsid("")
            }

        }
        connectivityManager.requestNetwork(networkRequest, networkCallback, 5000)
    }
}