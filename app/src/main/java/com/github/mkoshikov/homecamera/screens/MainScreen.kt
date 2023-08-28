package com.github.mkoshikov.homecamera.screens

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
import com.github.mkoshikov.homecamera.models.MainScreenTab
import com.github.mkoshikov.homecamera.models.MainViewModel
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
    ) {
        it
    }
}

private val AppBarHeight = 56.dp

// TODO: this should probably be part of the touch target of the start and end icons, clarify this
private val AppBarHorizontalPadding = 4.dp

// Start inset for the title when there is no navigation icon provided
private val TitleInsetWithoutIcon = Modifier.width(16.dp - AppBarHorizontalPadding)

// Start inset for the title when there is a navigation icon provided
private val TitleIconModifier = Modifier
    .fillMaxHeight()
    .width(72.dp - AppBarHorizontalPadding)

// The gap on all sides between the FAB and the cutout
private val BottomAppBarCutoutOffset = 8.dp

// How far from the notch the rounded edges start
private val BottomAppBarRoundedEdgeRadius = 4.dp