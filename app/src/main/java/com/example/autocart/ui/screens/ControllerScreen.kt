package com.example.autocart.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                actionOnPress = {viewModel.postMoveCommand(RobotMovement(magnitude="1", direction="left"))},
                actionOnRelease = {viewModel.postMoveCommand(RobotMovement(magnitude="0", direction="left"))},
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
                actionOnPress = {viewModel.postMoveCommand(RobotMovement(magnitude="1", direction="right"))},
                actionOnRelease = {viewModel.postMoveCommand(RobotMovement(magnitude="0", direction="right"))},
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
                actionOnPress = {viewModel.postMoveCommand(RobotMovement(magnitude="1", direction="forward"))},
                actionOnRelease = {viewModel.postMoveCommand(RobotMovement(magnitude="0", direction="forward"))},
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
                actionOnPress = {viewModel.postMoveCommand(RobotMovement(magnitude="1", direction="reverse"))},
                actionOnRelease = {viewModel.postMoveCommand(RobotMovement(magnitude="0", direction="reverse"))},
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