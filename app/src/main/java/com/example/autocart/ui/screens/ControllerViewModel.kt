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
import com.example.autocart.data.RobotMover
import com.example.autocart.model.RobotMovement
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ControllerViewModel(private val robotMover: RobotMover) : ViewModel() {
    /**
     * Do nothing on init
     */
    init {}

    /**
     * Post command request and update the mutable response
     */
    fun postMoveCommand(robotMovement: RobotMovement) : Boolean{
        var ret: Boolean = false
        viewModelScope.launch {
            try {
                var response = robotMover.postMoveCommand(robotMovement)
                ret = true
            } catch (e: IOException) {
            } catch (e: HttpException) {
            }
        }
        return ret
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