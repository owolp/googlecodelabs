package io.github.owolp.basicscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.owolp.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
//                MyScreenContent()
                MyScreenContentButtonOnTop()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    BasicsCodelabTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = Color.Yellow) {
            content()
        }
    }
}

@Composable
fun Greeting(name: String) {
    val isSelected = rememberSaveable { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected.value) Color.Red else Color.Transparent
    )

    Text(
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected.value = !isSelected.value }),
        text = "Hello $name!",
        style = MaterialTheme.typography.h6.copy(color = Color.Blue)
    )
}

@Preview(showBackground = true, name = "MyScreen Preview")
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}

@Composable
fun MyScreenContent(list: List<String> = List(100) { "Hello Android #$it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" }) {
//        Resets counter after config change
//        val counterState = remember {
//            mutableStateOf(0)
//        }

    val counterState = rememberSaveable {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        NameList(
            modifier = Modifier.weight(1f),
            names = list
        )

        Counter(
            count = counterState.value
        ) { newCount ->
            counterState.value = newCount
        }
    }
}

@Composable
fun MyScreenContentButtonOnTop(list: List<String> = List(100) { "Hello Android #$it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" }) {

    val counterState = rememberSaveable {
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier.fillMaxHeight()
    ) {
        NameList(
            names = list
        )

        Counter(
            modifier = Modifier.align(Alignment.BottomStart),
            count = counterState.value
        ) { newCount ->
            counterState.value = newCount
        }
    }
}

@Composable
fun NameList(
    modifier: Modifier = Modifier,
    names: List<String>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = names) { name ->
            Greeting(name = name)
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun Counter(
    modifier: Modifier = Modifier,
    count: Int,
    updateCount: (Int) -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {
        Text(text = "I've been clicked $count times")
    }
}