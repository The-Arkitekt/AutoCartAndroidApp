package com.example.autocart.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.autocart.R
import com.example.autocart.model.NetworkConfiguration

@Composable
fun DeviceSelectionScreen(
    modifier : Modifier = Modifier,
    viewModel: AutoCartViewModel
) {
    val buttonModifier = Modifier
        .height(64.dp)
        .width(256.dp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // get device names from string resource file
        val movementControllerName: String = stringResource(R.string.movement_controller)
        Text(stringResource(R.string.device_select_request))
        Spacer(Modifier.height(64.dp))
        Button(
            onClick = {viewModel.startConnectScreen(movementControllerName)},
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.movement_controller))
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = {viewModel.startHome()}) {
            Text(stringResource(R.string.home))
        }
    }
}

@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    deviceConfiguration: NetworkConfiguration,
    deviceName: String,
    viewModel: AutoCartViewModel) {

    val buttonModifier = Modifier
        .height(64.dp)
        .width(256.dp)

    var password: String by remember {mutableStateOf("")}

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.enter_password) + " " + deviceName,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        EditTextField(
            value = password,
            label = stringResource(id = R.string.password),
            onValueChange = {password = it}
        )
        Spacer(Modifier.height(64.dp))
        Button(
            onClick = {
                deviceConfiguration.key = password
                viewModel.connectToNetwork(deviceName, deviceConfiguration)
            },
            buttonModifier
        ) {
            Text(stringResource(R.string.connect))
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = { viewModel.startHome() }) {
            Text(stringResource(R.string.home))
        }
    }
}

@Composable
fun FailedToConnectScreen(
    modifier: Modifier = Modifier,
    viewModel: AutoCartViewModel,
    device: String
) {
    val buttonModifier = Modifier
        .height(64.dp)
        .width(256.dp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.failed_to_connect) + " " + device,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(64.dp))
        Button(
            onClick = {viewModel.startConnectScreen(device)},
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.try_again))
        }
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.startDeviceSelection()
            },
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.back))
        }
    }
}

@Composable
fun EditTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    TextField(
        label = {Text(label)},
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}