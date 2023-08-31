package com.github.mkoshikov.homecamera.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.mkoshikov.homecamera.R
import com.github.mkoshikov.homecamera.model.MainScreenTab
import com.github.mkoshikov.homecamera.model.MainViewModel
import com.github.mkoshikov.homecamera.screen.main.MainScreenCamerasTab
import com.github.mkoshikov.homecamera.screen.main.MainScreenDoorsTab
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(model: MainViewModel = koinViewModel()) {

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                )
                TabRow(
                    selectedTabIndex = MainScreenTab.values().indexOf(model.tab),
                ) {
                    MainScreenTab.values().forEach {
                        Tab(
                            selected = model.tab == it,
                            onClick = { model.selectTab(it) }
                        ) {
                            Box(
                                modifier = Modifier.height(44.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = stringResource(id = it.resId),
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        Crossfade(
            targetState = model.tab,
            label = "main_tabs"
            ) {
            when(it) {
                MainScreenTab.Cameras -> MainScreenCamerasTab(padding)
                MainScreenTab.Doors -> MainScreenDoorsTab(padding)
            }
        }
    }
}