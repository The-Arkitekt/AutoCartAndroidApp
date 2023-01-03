package com.example.autocart.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autocart.R

@Composable
fun HomeScreen(
    autoCartViewModel: AutoCartViewModel,
    modifier: Modifier = Modifier
) {
    /**
     * TESTING: set network configuration
     */
    autoCartViewModel.configureNetwork(
        ssid = "ESPWebServer",
        key = ""
    )
    when (autoCartViewModel.autoCartUIState) {
        is AutoCartUIState.Controller -> {
            val controllerViewModel: ControllerViewModel =
                viewModel(factory = ControllerViewModel.Factory)
            ControllerScreen(
                modifier,
                controllerViewModel
            )
        }
        else -> {
            val controllerViewModel: ControllerViewModel =
                viewModel(factory = ControllerViewModel.Factory)
            ControllerScreen(
                modifier,
                controllerViewModel
            )
        }
    }
}
