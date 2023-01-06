package com.example.autocart.ui.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionOnPressAndReleaseButton(modifier: Modifier = Modifier,
                                  button_content: @Composable() (RowScope.() -> Unit),
                                  onPressColor: Color,
                                  actionOnPress: () -> Unit,
                                  actionOnRelease: () -> Unit
): Unit {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var color = remember { mutableStateOf(Color.LightGray) }
    val posted = remember { mutableStateOf(false) }
    if (isPressed){
        if (!posted.value) {
            actionOnPress()
            color.value = onPressColor
            posted.value = true
        }

        //Use if + DisposableEffect to wait for the press action is completed
        DisposableEffect(Unit) {
            onDispose {
                actionOnRelease()
                color.value = Color.LightGray
                posted.value = false
            }
        }
    }
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color.value
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 20.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 15.dp,
            focusedElevation = 10.dp
        ),
        interactionSource = interactionSource,
        onClick = {},
        content = button_content,
    )
}