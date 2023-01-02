package com.example.autocart.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.autocart.R

@Composable
fun HomeScreen(
    autoCartUIState: AutoCartUIState,
    modifier: Modifier = Modifier
) {
    when (autoCartUIState) {
        is AutoCartUIState.Standby -> ControllerScreen(modifier)
        is AutoCartUIState.Success -> ControllerScreen(modifier)
        is AutoCartUIState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.error))
    }
}
