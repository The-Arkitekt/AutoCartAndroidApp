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
    data class EnableWifi(val retry: () -> Unit) : AutoCartUIState
    data class ConnectToDevice(
        val deviceName: String,
        val networkConfiguration: NetworkConfiguration
    ) : AutoCartUIState
    data class Error(val retry: () -> Unit) : AutoCartUIState
    data class FailedToConnect(val device: String) : AutoCartUIState
    object Standby         : AutoCartUIState
    object DeviceSelection : AutoCartUIState
    object Menu            : AutoCartUIState
    object Controller      : AutoCartUIState
}

class AutoCartViewModel(
    private val wifiConnection: WifiConnection,
    private val validDevices: Map<String, NetworkConfiguration>
) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var autoCartUIState: AutoCartUIState by mutableStateOf(AutoCartUIState.Menu)
        private set

    var liveConnectionStatus: AutoCartConnectionState by mutableStateOf(AutoCartConnectionState.NotConnected)
    var liveDevice: String by mutableStateOf("")

    fun updateLiveConnectionStatus(state: AutoCartConnectionState) {
        liveConnectionStatus = state
        if (state == AutoCartConnectionState.NotConnected) {
            updateLiveDevice("")
        }
    }
    fun updateLiveDevice(deviceName: String) {
        liveDevice = deviceName
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
    fun startDeviceSelection() {
        viewModelScope.launch {
            autoCartUIState = if (wifiConnection.isWifiAdapterEnabled()) {
                AutoCartUIState.DeviceSelection
            }
            else {
                AutoCartUIState.EnableWifi(retry = {startDeviceSelection()})
            }
        }
    }
    fun startConnectScreen(deviceName: String) {
        viewModelScope.launch {
            autoCartUIState = if (deviceName in validDevices) {
                AutoCartUIState.ConnectToDevice(deviceName, validDevices[deviceName] as NetworkConfiguration)
            }
            else {
                AutoCartUIState.Error(retry = {startDeviceSelection()})
            }
        }
    }
    fun startFailedToConnectScreen(device: String) {
        viewModelScope.launch {
            autoCartUIState = AutoCartUIState.FailedToConnect(device)
        }
    }
    fun startHome() {
        viewModelScope.launch {
            autoCartUIState = AutoCartUIState.Menu
        }
    }
    /**
     * Connect to configured network
     */
    fun connectToNetwork(deviceName: String, configuration: NetworkConfiguration) {
        val thisViewModel: AutoCartViewModel = this
        viewModelScope.launch {
            wifiConnection.connectToNetwork(deviceName, configuration, thisViewModel)
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
                val validDevices = application.container.validDevices
                AutoCartViewModel(
                    wifiConnection       = wifiConnection,
                    validDevices         = validDevices
                )
            }
        }
    }
}