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
        is AutoCartUIState.EnableWifi -> {
            EnableWifiScreen(modifier) {
                viewModel.startSignOn()
            }
        }
        is AutoCartUIState.SignOn -> {
            SignOnScreen(
                modifier,
                viewModel = viewModel
            )
        }
        is AutoCartUIState.Menu -> {
            MenuScreen(
                modifier,
                viewModel,
            )
        }
        is AutoCartUIState.Controller -> {
            ControllerScreen(
                modifier,
                viewModel(factory = ControllerViewModel.Factory),
                viewModel
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
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {viewModel.startSignOn()},
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.connect))
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

/**
 * TESTING

@Composable
fun MyComposeView(viewModel: AutoCartViewModel) {
    Column {

        viewModel.configureNetwork(ssid="testing", "passing")

        Button(onClick = { viewModel.connectToNetwork()}) {
            Text(text = "Click Me!")
        }

        Text(text = stringResource(viewModel.liveConnectionStatus.stringId))
    }
}
*/

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
