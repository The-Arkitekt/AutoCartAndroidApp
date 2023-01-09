package com.example.autocart.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autocart.R
import com.example.autocart.network.WifiConnection

@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    viewModel: AutoCartViewModel
) {
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    when (viewModel.autoCartUIState) {
        is AutoCartUIState.Standby -> {
            StandbyScreen(modifier)
        }
        is AutoCartUIState.Menu -> {
            MenuScreen(
                modifier,
                viewModel,
            )
        }
        is AutoCartUIState.EnableWifi -> {
            EnableWifiScreen(
                modifier,
                (viewModel.autoCartUIState as AutoCartUIState.EnableWifi).retry
            )
        }
        is AutoCartUIState.DeviceSelection -> {
            DeviceSelectionScreen(
                modifier,
                viewModel = viewModel
            )
        }
        is AutoCartUIState.ConnectToDevice -> {
            ConnectScreen(
                modifier,
                (viewModel.autoCartUIState as AutoCartUIState.ConnectToDevice).networkConfiguration,
                (viewModel.autoCartUIState as AutoCartUIState.ConnectToDevice).deviceName,
                viewModel
            )
        }
        is AutoCartUIState.FailedToConnect -> {
            FailedToConnectScreen(
                modifier,
                viewModel,
                (viewModel.autoCartUIState as AutoCartUIState.FailedToConnect).device
            )
        }
        is AutoCartUIState.Controller -> {
            ControllerScreen(
                modifier,
                viewModel(factory = ControllerViewModel.Factory),
                viewModel
            )
        }
        is AutoCartUIState.Error -> {
            ErrorScreen(
                modifier,
                (viewModel.autoCartUIState as AutoCartUIState.Error).retry
            )
        }
    }
}

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    viewModel: AutoCartViewModel
) {
    val buttonModifier = Modifier
        .height(64.dp)
        .width(256.dp)

    /**
     * Start of UI design
     */
    Column(
        modifier = modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {viewModel.startDeviceSelection()},
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.connect_to_device))
        }
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {viewModel.startController()},
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.controller))
        }
    }
}

@Composable
fun StandbyScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.ic_baseline_change_circle_24),
            contentDescription = stringResource(R.string.standing_by)
        )
    }
}

@Composable
fun EnableWifiScreen(modifier: Modifier = Modifier, retry: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.enable_wifi))
        Button(onClick = retry) {
            Text(stringResource(R.string._continue))
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, retry: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.error))
        Button(onClick = retry) {
            Text(stringResource(R.string.back))
        }
    }
}
