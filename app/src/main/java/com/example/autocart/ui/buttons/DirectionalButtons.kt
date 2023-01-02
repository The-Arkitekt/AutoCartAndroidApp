package com.example.autocart.ui.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.autocart.R


val horizontalButtonModifier = Modifier
    .height(128.dp)
    .width(100.dp)

val verticalButtonModifier = Modifier
    .height(100.dp)
    .width(128.dp)

@Composable
fun MoveLeftButton() {
    ActionOnPressAndReleaseButton(
        modifier = horizontalButtonModifier,
        actionOnPress = {true},
        actionOnRelease = {true},
        button_content = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_left_24),
                contentDescription = stringResource(id = R.string.left)
            )
        }
    )
}
@Composable
fun MoveRightButton() {
    ActionOnPressAndReleaseButton(
        modifier = horizontalButtonModifier,
        actionOnPress = {true},
        actionOnRelease = {true},
        button_content = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                contentDescription = stringResource(id = R.string.right)
            )
        }
    )
}
@Composable
fun MoveForwardButton() {
    ActionOnPressAndReleaseButton(
        modifier = verticalButtonModifier,
        actionOnPress = {true},
        actionOnRelease = {true},
        button_content = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24),
                contentDescription = stringResource(id = R.string.up)
            )
        }
    )
}
@Composable
fun MoveBackButton() {
    ActionOnPressAndReleaseButton(
        modifier = verticalButtonModifier,
        actionOnPress = {true},
        actionOnRelease = {true},
        button_content = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
                contentDescription = stringResource(id = R.string.down)
            )
        }
    )
}