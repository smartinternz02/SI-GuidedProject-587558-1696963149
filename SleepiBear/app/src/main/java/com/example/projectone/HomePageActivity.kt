package com.example.projectone

import com.example.projectone.ui.theme.ProjectOneTheme
import android.content.Intent
import androidx.compose.material.Button
import androidx.compose.material.Text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class HomePageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectOneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomePage()
                }
            }
        }
    }
}

@Composable
fun HomePage() {
    val context = LocalContext.current
    Image(
        painterResource(id = R.drawable.peakpx),
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier
            .alpha(0.4F),
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 35.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            text = "Choose Your Poison !"
        )
        Spacer(modifier = Modifier.height(25.dp))

        Button(onClick = {
            // Open MainActivity
            context.startActivity(
                Intent(context, MainActivity::class.java)
            )
        },
            modifier = Modifier
                .width(260.dp)
                .height(100.dp)
        ) {
            Text(text = "Sleep Tracking",
                fontSize = 23.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Open MusicPlayerActivity
            context.startActivity(
                Intent(context, MusicActivity::class.java)
            )
        },
            modifier = Modifier
                .width(260.dp)
                .height(100.dp)
            ) {
            Text(text = "Soothing Music",
                fontSize = 23.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Open com.example.projectone.SmartAlarmActivity
            context.startActivity(
                Intent(context, SmartAlarmActivity::class.java)
            )
        },
            modifier = Modifier
                .width(260.dp)
                .height(100.dp)
        ) {
            Text(text = "Smart Alarm",
                fontSize = 23.sp)
        }
    }
}
