package com.example.autocart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.autocart.ui.AutoCartApp
import com.example.autocart.ui.theme.AutoCartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoCartTheme {
                AutoCartApp()
            }
        }
    }
}

/*
@Composable
fun ControllerWithButtons(modifier: Modifier = Modifier) {
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

*/
/*
val horizontalButtonModifier = Modifier
    .height(128.dp)
    .width(100.dp)

val verticalButtonModifier = Modifier
    .height(100.dp)
    .width(128.dp)

@Composable
fun MoveLeftButton() {
    ColoredActionButton(
        action_target = 0, modifier = horizontalButtonModifier
        ){
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_left_24),
            contentDescription = stringResource(id = R.string.left)
        )
    }
}
@Composable
fun MoveRightButton() {
    ColoredActionButton(
        action_target = 1,
        modifier = horizontalButtonModifier
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
            contentDescription = stringResource(id = R.string.right)
        )
    }
}
@Composable
fun MoveForwardButton() {
    ColoredActionButton(
        action_target = 2,
        modifier = verticalButtonModifier
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24),
            contentDescription = stringResource(id = R.string.up)
        )
    }
}
@Composable
fun MoveBackButton() {
    ColoredActionButton(
        modifier = verticalButtonModifier
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
            contentDescription = stringResource(id = R.string.down)
        )
    }
}
*/
/*
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColoredActionButton(modifier: Modifier = Modifier,
                  button_content: @Composable() (RowScope.() -> Unit)
): Unit {
    val http = remember{ Http() }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = remember { mutableStateOf(Color.LightGray) }
    val posted = remember { mutableStateOf(false)}
    fun postOn (): Boolean{
        return http.setValue(setValue="1")
    }
    fun postOff (): Boolean{
        return http.setValue(setValue="0")
    }
    if (isPressed){
        if (!posted.value) {
            if (postOn()) {
                color.value = Color.Green
            }
            else {
                color.value = Color.Red
            }
            posted.value = true
        }

        //Use if + DisposableEffect to wait for the press action is completed
        DisposableEffect(Unit) {
            onDispose {
                color.value = Color.LightGray
                postOff()
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
*/
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AutoCartTheme {
        AutoCartApp()
    }
}