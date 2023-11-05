// MusicActivity.kt

package com.example.projectone

import android.media.MediaPlayer
import android.os.Bundle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.projectone.ui.theme.ProjectOneTheme


class MusicActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectOneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MusicPlayerApp()
                }
            }
        }
    }
}

@Composable
fun MusicPlayerApp() {
    val context = LocalContext.current

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    Image(
        painterResource(id = R.drawable.tiles),
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier
            .alpha(0.2F),
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.msuic),
            contentDescription = "",
            modifier = Modifier
                .width(260.dp)
                .height(180.dp)
                .padding(10.dp)
        )
        Button(
            onClick = {
                if (isPlaying) {
                    mediaPlayer?.pause()
                } else {
                    mediaPlayer?.start()
                }
                isPlaying = !isPlaying
            },
            modifier = Modifier
                .width(260.dp)
                .height(100.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(fontSize = 26.sp, text = if (isPlaying) "Pause" else "Play")
        }

        Button(
            onClick = {
                mediaPlayer?.seekTo(0)
                mediaPlayer?.start()
                isPlaying = true
            },
            modifier = Modifier
                .width(240.dp)
                .height(90.dp)
                .padding(13.dp)
        ) {
            Text(fontSize = 25.sp, text = "Restart")
        }
    }

    LaunchedEffect(Unit) {
        mediaPlayer = MediaPlayer.create(context, R.raw.rain)
        mediaPlayer?.setOnCompletionListener {
            isPlaying = false
        }
    }
}