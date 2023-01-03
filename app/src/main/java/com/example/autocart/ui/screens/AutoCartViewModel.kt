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
import com.example.autocart.data.RobotMover

/**
 * UI state for the Home screen
 */
sealed interface AutoCartUIState {
    object Controller : AutoCartUIState
}

class AutoCartViewModel() : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var autoCartUIState: AutoCartUIState by mutableStateOf(AutoCartUIState.Controller)
        private set

    /**
     * Do nothing on init
     */
    init {}

    /**
     * Factory for [ControllerViewModel] that takes [RobotMover] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AutoCartApplication)
                AutoCartViewModel()
            }
        }
    }
}