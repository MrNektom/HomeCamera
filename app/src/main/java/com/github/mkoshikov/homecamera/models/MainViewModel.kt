package com.github.mkoshikov.homecamera.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.mkoshikov.homecamera.R

class MainViewModel : ViewModel() {
    private var _tab by mutableStateOf(MainScreenTab.Cameras)
    val tab get() = _tab

    fun selectTab(tab: MainScreenTab) {
        _tab = tab
    }
}

enum class MainScreenTab(
    val resId: Int
) {
    Cameras(R.string.cameras),
    Doors(R.string.doors);
}