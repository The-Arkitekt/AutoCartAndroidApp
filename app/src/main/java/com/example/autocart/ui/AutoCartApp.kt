package com.example.autocart.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autocart.ui.screens.AutoCartViewModel
import com.example.autocart.ui.screens.HomeScreen
import com.example.autocart.R

@Composable
fun AutoCartApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar   = { TopAppBar(title = { Text(stringResource(R.string.app_name))})}
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            val autoCartViewModel: AutoCartViewModel =
                viewModel(factory = AutoCartViewModel.Factory)
            HomeScreen(
                autoCartViewModel = autoCartViewModel,
            )
        }
    }
}