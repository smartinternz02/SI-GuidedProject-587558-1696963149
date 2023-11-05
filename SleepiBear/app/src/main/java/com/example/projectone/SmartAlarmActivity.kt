// SmartAlarmActivity.kt

package com.example.projectone

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectone.ui.theme.ProjectOneTheme
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

class SmartAlarmActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectOneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp(this)
                }
            }
        }
    }
}

@Composable
fun MyApp(context: Context) {
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.alarmfour) }
    val eightHourMillis = /*8 * 60 * 60 **/ 1000L // 1 sec instead of 8 hours in milliseconds
    var alarmTime by remember { mutableStateOf(0L) }
    var alarmTriggered by remember { mutableStateOf(false) }
    var answer by remember { mutableStateOf("") }
    var mathProblem by remember { mutableStateOf("") }
    Image(
        painterResource(id = R.drawable.blueblack),
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
        Image(
            painter = painterResource(id = R.drawable.alarmclock),
            contentDescription = "",
            modifier = Modifier
                .width(260.dp)
                .height(260.dp)
                .padding(10.dp)
        )
        if (alarmTriggered) {
            Text(text = mathProblem)
            TextField(value = answer, onValueChange = { answer = it })
            Button(
                onClick = {
                    if (answer == getMathProblemAnswer(mathProblem)) {
                        alarmTime = 0L
                        answer = ""
                        alarmTriggered = false
                        stopAlarm(mediaPlayer)
                    } else {
                        answer = ""
                        Toast.makeText(context, "Wrong Answer", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Submit")
            }
        } else {
            val alarmTimeString = SimpleDateFormat("HH:mm:ss").format(
                Date(System.currentTimeMillis() + eightHourMillis)
            )
            Text(fontSize = 30.sp, text = "Alarm will ring at $alarmTimeString")
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .width(260.dp)
                    .height(100.dp),
                onClick = {
                    mathProblem = generateMathProblem()
                    alarmTriggered = true
                    alarmTime = System.currentTimeMillis() + eightHourMillis
                    startCountdownTimer(alarmTime - System.currentTimeMillis(), mediaPlayer)
                }
            ) {
                Text(fontSize = 25.sp, text = "Set Alarm")
            }
        }
    }
}

private fun generateMathProblem(): String {
    val num1 = Random.nextInt(1, 11)
    val num2 = Random.nextInt(1, 11)
    return "$num1 + $num2 = ?"
}

private fun getMathProblemAnswer(mathProblem: String): String {
    val equationParts = mathProblem.split(" + ")
    val num1 = equationParts[0].toInt()
    val num2 = equationParts[1].split(" = ")[0].toInt()
    return (num1 + num2).toString()
}

private fun startAlarm(mediaPlayer: MediaPlayer) {
    // Start playing the alarm sound
    mediaPlayer.start()
}

private fun stopAlarm(mediaPlayer: MediaPlayer) {
    // Stop playing the alarm sound
    mediaPlayer.stop()
    mediaPlayer.prepare()
}

private fun startCountdownTimer(duration: Long, mediaPlayer: MediaPlayer) {
    object : CountDownTimer(duration, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            startAlarm(mediaPlayer)
        }
    }.start()
}
