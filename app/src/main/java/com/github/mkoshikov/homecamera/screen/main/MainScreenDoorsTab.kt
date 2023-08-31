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
import com.github.mkoshikov.homecamera.component.door.DoorCard
import com.github.mkoshikov.homecamera.component.door.DoorEditingDialog
import com.github.mkoshikov.homecamera.model.main.DoorsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenDoorsTab(
    padding: PaddingValues,
    model: DoorsViewModel = koinViewModel()
) {
    val doors by model.doors.collectAsState()
    val error = model.error
    val editing = model.editing

    val pullRefreshState = rememberPullRefreshState(
        refreshing = model.refreshing,
        onRefresh = model::refresh
    )

    if (model.loading) {
        FullSizeCircularProgressIndicator()
    }

    if (error != null) {
        Text(
            text = error.stackTraceToString(),
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
        )
    }

    Box(
        modifier = Modifier
            .pullRefresh(
                state = pullRefreshState
            )
    ) {

        LazyColumn(
            contentPadding = PaddingValues(
                top = 8.dp + padding.calculateTopPadding(),
                bottom = 8.dp + padding.calculateBottomPadding(),
                start = 8.dp,
                end = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(doors) { door ->
                DoorCard(
                    door = door,
                    onStartEditing = model::editing::set,
                    onUpdateFavorites = model::updateDoorFavorites,
                    onUpdateLocked = model::updateDoorLocked
                    )
            }
        }

        PullRefreshIndicator(
            refreshing = model.refreshing,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }

    if (editing != null) {
        DoorEditingDialog(
            door = editing,
            onDismissRequest = { model.editing = null },
            onUpdateName = model::updateDoorName
        )
    }
}