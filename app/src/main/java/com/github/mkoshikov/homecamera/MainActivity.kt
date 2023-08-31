package com.github.mkoshikov.homecamera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.mkoshikov.homecamera.screen.MainScreen
import com.github.mkoshikov.homecamera.ui.theme.HomeCameraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeCameraTheme {
                MainScreen()
            }
        }
    }
}

