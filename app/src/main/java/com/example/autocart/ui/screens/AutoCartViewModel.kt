package com.example.autocart.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.autocart.AutoCartApplication
import com.example.autocart.data.AppContainer
import com.example.autocart.data.RobotMover
import com.example.autocart.model.NetworkConfiguration

/**
 * UI state for the Home screen
 */
sealed interface AutoCartUIState {
    object Controller : AutoCartUIState
}

class AutoCartViewModel(appContainer: AppContainer) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var autoCartUIState: AutoCartUIState by mutableStateOf(AutoCartUIState.Controller)
        private set

    private val appContainer: AppContainer = appContainer

    /**
     * Do nothing on init
     */
    init {}

    fun configureNetwork(ssid: String, key: String) {
        appContainer.networkConfiguration.ssid = ssid
        appContainer.networkConfiguration.key = key
    }

    /**
     * Factory for [ControllerViewModel] that takes [RobotMover] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AutoCartApplication)
                AutoCartViewModel(appContainer=application.container)
            }
        }
    }
}