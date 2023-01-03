package com.example.autocart.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.autocart.R
import com.example.autocart.model.RobotMovement

import com.example.autocart.ui.buttons.*

@Composable
fun ControllerScreen(
    modifier: Modifier = Modifier,
    viewModel: ControllerViewModel
) {
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    val horizontalButtonModifier = Modifier
        .height(128.dp)
        .width(100.dp)

    val verticalButtonModifier = Modifier
        .height(100.dp)
        .width(128.dp)

    val onPressColor = Color.Green

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
            /**
             * MoveLeftButton
             */
            ActionOnPressAndReleaseButton(
                modifier = horizontalButtonModifier,
                onPressColor = onPressColor,
                actionOnPress = {viewModel.leftButtonMoveCommand(magnitude="1")},
                actionOnRelease = {viewModel.leftButtonMoveCommand(magnitude="0")},
                button_content = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_left_24),
                        contentDescription = stringResource(id = R.string.left)
                    )
                }
            )
            /**
             * MoveRightButton
             */
            ActionOnPressAndReleaseButton(
                modifier = horizontalButtonModifier,
                onPressColor = onPressColor,
                actionOnPress = {viewModel.rightButtonMoveCommand(magnitude="1")},
                actionOnRelease = {viewModel.rightButtonMoveCommand(magnitude="0")},
                button_content = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                        contentDescription = stringResource(id = R.string.right)
                    )
                }
            )

        }
        Spacer(Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            /**
             * MoveForwardButton
             */
            ActionOnPressAndReleaseButton(
                modifier = verticalButtonModifier,
                onPressColor = onPressColor,
                actionOnPress = {viewModel.upButtonMoveCommand(magnitude="1")},
                actionOnRelease = {viewModel.upButtonMoveCommand(magnitude="0")},
                button_content = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24),
                        contentDescription = stringResource(id = R.string.up)
                    )
                }
            )
            /**
             * MoveBackButton
             */
            ActionOnPressAndReleaseButton(
                modifier = verticalButtonModifier,
                onPressColor = onPressColor,
                actionOnPress = {viewModel.downButtonMoveCommand(magnitude="1")},
                actionOnRelease = {viewModel.downButtonMoveCommand(magnitude="0")},
                button_content = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
                        contentDescription = stringResource(id = R.string.down)
                    )
                }
            )

        }
    }
}