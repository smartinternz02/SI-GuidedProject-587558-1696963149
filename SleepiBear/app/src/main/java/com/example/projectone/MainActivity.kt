package com.example.projectone

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.projectone.ui.theme.ProjectOneTheme
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var databaseHelper: TimeLogDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = TimeLogDatabaseHelper(this)
        databaseHelper.deleteAllData()
        setContent {
            ProjectOneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyScreen(this,databaseHelper)
                }
            }
        }
    }
}
@Composable
fun MyScreen(context: Context, databaseHelper: TimeLogDatabaseHelper) {
    var startTime by remember { mutableStateOf(0L) }
    var elapsedTime by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    val imageModifier = Modifier
    Image(
        painterResource(id = R.drawable.greenblack),
        contentScale = ContentScale.FillHeight,
        contentDescription = "",
        modifier = imageModifier
            .alpha(0.2F),
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 45.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Cursive,
            color = Color.Yellow,
            text = "Sleep Time Tracking",
            modifier = Modifier.padding(10.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.timer),
            contentDescription = "",

            modifier = imageModifier
                .width(190.dp)
                .height(200.dp)
                .padding(10.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFFFFCC3B),
                            Color(0xFF5BC27E),
                            Color(0xFFFFCC3B)
                        )
                    )
                )
        )
        Spacer(modifier = Modifier.height(18.dp))
        if (!isRunning) {
            Button(modifier = Modifier
                .width(260.dp)
                .height(100.dp),
                onClick = {
                startTime = System.currentTimeMillis()
                isRunning = true
            }) {
                Text(text = "Start", fontSize = 25.sp)
                //databaseHelper.addTimeLog(startTime)
            }
        } else {
            Button(modifier = Modifier
                .width(260.dp)
                .height(100.dp),
                onClick = {
                elapsedTime = System.currentTimeMillis()
                isRunning = false
            }) {
                Text(text = "Stop", fontSize = 25.sp)
                databaseHelper.addTimeLog(elapsedTime,startTime)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            text = "Elapsed Time: ${
                formatTime(elapsedTime - startTime)
            }"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(modifier = Modifier
            .width(260.dp)
            .height(100.dp),
            onClick = { context.startActivity(
            Intent(
                context,
                TrackActivity::class.java
            )
        ) }) {
            Text(fontSize = 25.sp, text = "Track Sleep")
        }
        Spacer(modifier = Modifier.height(40.dp))

    }

}

private fun startTrackActivity(context: Context) {
    val intent = Intent(context, TrackActivity::class.java)
    ContextCompat.startActivity(context, intent, null)
}
fun getCurrentDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentTime = System.currentTimeMillis()
    return dateFormat.format(Date(currentTime))
}

fun formatTime(timeInMillis: Long): String {
    val hours = (timeInMillis / (1000 * 60 * 60)) % 24
    val minutes = (timeInMillis / (1000 * 60)) % 60
    val seconds = (timeInMillis / 1000) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}


