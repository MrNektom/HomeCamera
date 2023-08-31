package com.github.mkoshikov.homecamera.screen.main

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.mkoshikov.homecamera.component.FullSizeCircularProgressIndicator
import com.github.mkoshikov.homecamera.component.camera.CameraCard
import com.github.mkoshikov.homecamera.model.main.CamerasViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenCamerasTab(
    padding: PaddingValues,
    model: CamerasViewModel = koinViewModel()
) {
    val cameras by model.savedCameras.collectAsState()
    val error = model.error

    if (model.loading) FullSizeCircularProgressIndicator()

    val refreshState = rememberPullRefreshState(
        refreshing = model.refreshing,
        onRefresh = model::refresh
    )

    Box(modifier = Modifier.pullRefresh(refreshState)) {
        if (error != null) {
            Text(
                text = error.stackTraceToString(),
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 8.dp + padding.calculateTopPadding(),
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp + padding.calculateBottomPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cameras) { camera ->
                    CameraCard(
                        camera = camera,
                        onChangeFavorites = {
                            model.onChangeFavorites(camera, it)
                        }
                    )
                }
            }
        }

        PullRefreshIndicator(
            refreshing = model.refreshing,
            state = refreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}