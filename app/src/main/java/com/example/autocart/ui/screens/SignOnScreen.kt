package com.example.autocart.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.autocart.R

@Composable
fun SignOnScreen(
    modifier : Modifier = Modifier,
    viewModel: AutoCartViewModel
) {
    var ssid by remember{ mutableStateOf("") }
    var key by remember{ mutableStateOf("")}

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.sign_in_request))
        EditTextField(
            value = ssid,
            label = stringResource(id = R.string.ssid),
            onValueChange = {ssid = it}
        )
        EditTextField(
            value = key,
            label = stringResource(id = R.string.key),
            onValueChange = {key = it}
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            viewModel.configureNetwork(ssid, key)
            viewModel.connectToNetwork()
        }) {
            Text(stringResource(R.string.sign_in))
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {viewModel.startHome()}) {
            Text(stringResource(R.string.home))
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