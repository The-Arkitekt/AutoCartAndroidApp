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

sealed interface ButtonState {
    data class Try(val response: Response<ResponseBody>) : ButtonState
    object Success: ButtonState
    object Error : ButtonState
    object NotPressed : ButtonState
}

class ButtonModel (robotMover: RobotMover) {
    private val thisRobotMover: RobotMover = robotMover
    var state: ButtonState by mutableStateOf(ButtonState.NotPressed)

    suspend fun moveCommand(robotMovement: RobotMovement) {
        this.state = try {
            ButtonState.Try(thisRobotMover.postMoveCommand(robotMovement))
        } catch (e: IOException) {
            ButtonState.Error
        } catch (e: HttpException) {
            ButtonState.Error
        }
        this.state = ButtonState.Success
    }
}

class ControllerViewModel(
    private val connection: WifiConnection,
    private val networkConfiguration: NetworkConfiguration,
    private val robotMover: RobotMover) : ViewModel() {
    /**
     * Create Button objects
     */
    private val leftButton  = ButtonModel(robotMover)
    private val rightButton = ButtonModel(robotMover)
    private val upButton    = ButtonModel(robotMover)
    private val downButton  = ButtonModel(robotMover)

    /**
     * Connect to IOT network on Init()
     */
    init {
        viewModelScope.launch() {
            connection.connectToNetwork(networkConfiguration)
        }
    }
    /**
     * Getters and Setters
     */
    fun connectionStatus() : Boolean{
        return connection.connected
    }

    /**
     * wrappers for moveCommands of ButtonModel objects in ViewModelScope
     */
    fun leftButtonMoveCommand(magnitude: String) {
        val robotMovement: RobotMovement = RobotMovement(magnitude, direction="0")
        viewModelScope.launch {
            leftButton.moveCommand(robotMovement)
        }
    }
    fun rightButtonMoveCommand(magnitude: String) {
        val robotMovement: RobotMovement = RobotMovement(magnitude, direction="2")
        viewModelScope.launch {
            rightButton.moveCommand(robotMovement)
        }
    }
    fun upButtonMoveCommand(magnitude: String) {
        val robotMovement: RobotMovement = RobotMovement(magnitude, direction="1")
        viewModelScope.launch {
            upButton.moveCommand(robotMovement)
        }
    }
    fun downButtonMoveCommand(magnitude: String) {
        val robotMovement: RobotMovement = RobotMovement(magnitude, direction="3")
        viewModelScope.launch {
            downButton.moveCommand(robotMovement)
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
                val connection = application.container.connection
                val networkConfiguration = application.container.networkConfiguration
                ControllerViewModel(
                    connection = connection,
                    networkConfiguration = networkConfiguration,
                    robotMover = robotMover
                )
            }
        }
    }
}