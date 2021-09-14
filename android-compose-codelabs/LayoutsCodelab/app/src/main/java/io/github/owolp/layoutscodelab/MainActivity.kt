package io.github.owolp.layoutscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import io.github.owolp.layoutscodelab.ui.theme.LayoutsCodelabTheme
import kotlinx.coroutines.launch

@ExperimentalCoilApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LayoutsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LayoutsCodelab(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LayoutsCodelabTheme {
        LayoutsCodelab()
    }
}

@ExperimentalCoilApi
@Composable
fun LayoutsCodelab(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(
                        onClick = {
                            /*TODO*/
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        PhotographerList(modifier.padding(innerPadding))
    }
}

@ExperimentalCoilApi
@Composable
fun PhotographerList(modifier: Modifier = Modifier) {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Scroll to Top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    scrollState.scrollToItem(listSize - 1)
                }
            }) {
                Text(text = "Scroll to Bottom")
            }
        }
        LazyColumn(state = scrollState) {
            items(listSize) { position ->
                PhotographerCard(modifier, position.toString())
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun PhotographerCard(
    modifier: Modifier = Modifier,
    minutes: String,
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = {})
            .padding(16.dp)
    ) {
//        Surface(
//            modifier = Modifier
//                .size(50.dp),
//            shape = CircleShape,
//            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
//        ) {
//            Image(
//                painter = rememberImagePainter(
//                    data = "https://developer.android.com/images/brand/Android_Robot.png"
//                ),
//                contentDescription = null
//            )
//        }

        Image(
            modifier = Modifier.size(50.dp),
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png",
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                    transformations(RoundedCornersTransformation())
                }
            ),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)

            /**
             * Access to theme's (in this case Material Themes' alpha content)
             * and provides it to it's children.
             */
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "$minutes minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}