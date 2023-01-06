package com.example.autocart.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.autocart.AutoCartApplication
import com.example.autocart.MainActivity
import com.example.autocart.data.RobotMover
import com.example.autocart.model.NetworkConfiguration
import com.example.autocart.model.RobotMovement
import com.example.autocart.network.WifiConnection
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ControllerViewModel(private val robotMover: RobotMover) : ViewModel() {
    /**
     * Connect to IOT network on Init()
     */
    init {}

    fun postMoveCommand(robotMovement: RobotMovement) {
        viewModelScope.launch {
            robotMover.postMoveCommand(robotMovement)
        }
    }
    /**
     * Factory for [ControllerViewModel] that takes [RobotMover] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AutoCartApplication)
                val robotMover = application.container.robotMover
                ControllerViewModel(robotMover = robotMover)
            }
        }
    }
}