package com.example.autocart.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.autocart.AutoCartApplication
import com.example.autocart.Model.RobotMovement
import com.example.autocart.data.RobotMover
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface AutoCartUIState {
    data class Success(val response: Response<ResponseBody>) : AutoCartUIState
    object Standby : AutoCartUIState
    object Error : AutoCartUIState
}

class AutoCartViewModel(private val robotMover: RobotMover) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var autoCartUIState: AutoCartUIState by mutableStateOf(AutoCartUIState.Standby)
        private set

    /**
     * Do nothing on init
     */
    init {}

    /**
     * Post command request and update the mutable response
     */
    fun postMoveCommand(robotMovement: RobotMovement) {
        viewModelScope.launch {
            autoCartUIState = AutoCartUIState.Standby
            autoCartUIState = try {
                AutoCartUIState.Success(robotMover.postMoveCommand(robotMovement.contents))
            } catch (e: IOException) {
                AutoCartUIState.Error
            } catch (e: HttpException) {
                AutoCartUIState.Error
            }
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
                AutoCartViewModel(robotMover = robotMover)
            }
        }
    }
}