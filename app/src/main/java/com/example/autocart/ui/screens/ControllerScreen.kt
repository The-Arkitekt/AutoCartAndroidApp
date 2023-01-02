package com.example.autocart.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.autocart.ui.buttons.MoveBackButton
import com.example.autocart.ui.buttons.MoveForwardButton
import com.example.autocart.ui.buttons.MoveLeftButton
import com.example.autocart.ui.buttons.MoveRightButton

@Composable
fun ControllerScreen(modifier: Modifier = Modifier) {
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    Row(
        modifier = modifier
            .padding(horizontal = 64.dp, vertical = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            MoveLeftButton()
            MoveRightButton()
        }
        Spacer(Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            MoveForwardButton()
            MoveBackButton()
        }
    }
}