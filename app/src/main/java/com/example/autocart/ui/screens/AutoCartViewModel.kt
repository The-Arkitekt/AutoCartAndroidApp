package com.example.autocart.ui.screens

import android.net.wifi.WifiManager
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.autocart.AutoCartApplication
import com.example.autocart.data.AppContainer
import com.example.autocart.data.RobotMover
import com.example.autocart.model.NetworkConfiguration
import com.example.autocart.network.WifiConnection
import kotlinx.coroutines.launch
import com.example.autocart.R

/**
 * Connection state
 */
sealed interface AutoCartConnectionState {
    abstract val stringId: Int

    object NotConnected : AutoCartConnectionState {
        override val stringId: Int = R.string.not_connected
    }
    object Connecting : AutoCartConnectionState {
        override val stringId: Int = R.string.connecting
    }
    object Connected : AutoCartConnectionState {
        override val stringId: Int = R.string.connected_to
    }
}

/**
 * UI state for the Home screen
 */
sealed interface AutoCartUIState {
    object Standby     : AutoCartUIState
    object EnableWifi  : AutoCartUIState
    object SignOn      : AutoCartUIState
    object Menu        : AutoCartUIState
    object Controller  : AutoCartUIState
}

class AutoCartViewModel(
    val wifiConnection: WifiConnection,
    private val networkConfiguration: NetworkConfiguration
) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var autoCartUIState: AutoCartUIState by mutableStateOf(AutoCartUIState.Menu)
        private set

    private val connectionState = if(wifiConnection.connected()) {
        AutoCartConnectionState.Connected
    } else {
        AutoCartConnectionState.NotConnected
    }
    var liveConnectionStatus: AutoCartConnectionState by mutableStateOf(connectionState)
    var liveSsid: String by mutableStateOf(wifiConnection.configuration().ssid)

    fun updateLiveConnectionStatus(state: AutoCartConnectionState) {
        liveConnectionStatus = state
    }
    fun updateLiveSsid(ssid: String) {
        liveSsid = ssid
    }

    /**
     * Do nothing on init
     */
    init {}


    /**
     * Enter Controller state
     */
    fun startController() {
        viewModelScope.launch {
            autoCartUIState = AutoCartUIState.Controller
        }
    }
    fun startSignOn() {
        viewModelScope.launch {
            checkWifiAdapter()
        }
    }
    fun startHome() {
        viewModelScope.launch {
            autoCartUIState = AutoCartUIState.Menu
        }
    }
    /**
     * Check if Wifi adapter is on
     */
    private fun checkWifiAdapter() {
        viewModelScope.launch {
            autoCartUIState = if (wifiConnection.isWifiAdapterEnabled()) {
                AutoCartUIState.SignOn
            } else {
                AutoCartUIState.EnableWifi
            }
        }
    }
    /**
     * Configure AP to connect to
     */
    fun configureNetwork(ssid: String, key: String) {
        networkConfiguration.ssid = ssid
        networkConfiguration.key = key
    }
    /**
     * Connect to configured network
     */
    fun connectToNetwork() {
        val thisViewModel: AutoCartViewModel = this
        viewModelScope.launch {
            wifiConnection.connectToNetwork(networkConfiguration, thisViewModel)
            autoCartUIState = AutoCartUIState.Menu
        }
    }
    /**
     * Factory for [ControllerViewModel] that takes [RobotMover] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AutoCartApplication)
                val wifiConnection = application.container.connection
                val networkConfiguration = application.container.networkConfiguration
                AutoCartViewModel(
                    wifiConnection       = wifiConnection,
                    networkConfiguration = networkConfiguration
                )
            }
        }
    }
}