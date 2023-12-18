package com.example.chrismasvoice

import androidx.compose.foundation.Image
import AudioRecorder
import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private var audioRecorder: AudioRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var filePath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audioRecorder = AudioRecorder()
        filePath = "${filesDir.absolutePath}/test_recording.3gp"

        setContent {
            RecordScreen()
        }
    }

    @Composable
    fun RecordScreen() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.christmas_snow),
                contentDescription = "Christmas snow",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = { startRecording() }) {
                    Text("録音開始")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { stopRecording() }) {
                    Text("録音停止")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { playRecording() }) {
                    Text("再生")
                }
            }
        }
    }

    private fun startRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 200)
        } else {
            audioRecorder?.startRecording(filePath)
        }
    }

    private fun stopRecording() {
        audioRecorder?.stopRecording()
    }

    private fun playRecording() {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(filePath)
            prepare()
            start()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecording()
        }
    }
}
