package com.example.autocart.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autocart.AutoCartApplication
import com.example.autocart.ui.screens.AutoCartViewModel
import com.example.autocart.ui.screens.HomeScreen
import com.example.autocart.R
import com.example.autocart.network.WifiConnection

@Composable
fun AutoCartApp(modifier: Modifier = Modifier) {
    /**
     * Create main View Model
     */
    val autoCartViewModel: AutoCartViewModel =
        viewModel(factory = AutoCartViewModel.Factory)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar   = {
            TopAppBar(title = {
                Text(
                    stringResource(R.string.app_name) + " : "
                            + stringResource(autoCartViewModel.liveConnectionStatus.stringId) + " "
                            + autoCartViewModel.liveSsid
                )
            })
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(
                modifier = modifier,
                viewModel = autoCartViewModel
            )
        }
    }
}
