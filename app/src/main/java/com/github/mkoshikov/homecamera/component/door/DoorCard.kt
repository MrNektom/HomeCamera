package com.github.mkoshikov.homecamera.component.door

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.mkoshikov.homecamera.component.SwipeState
import com.github.mkoshikov.homecamera.component.SwipeToAction
import com.github.mkoshikov.homecamera.component.camera.StarColor
import com.github.mkoshikov.homecamera.repository.`object`.Door
import com.github.mkoshikov.homecamera.ui.theme.BrandColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoorCard(
    door: Door,
    onUpdateFavorites: (Door, Boolean) -> Unit,
    onUpdateLocked: (Door, Boolean) -> Unit,
    onStartEditing: (Door) -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = SwipeState.DEFAULT)
    val scope = rememberCoroutineScope()

    fun animateToDefault() {
        scope.launch {
            swipeableState.animateTo(SwipeState.DEFAULT)
        }
    }

    SwipeToAction(
        state = swipeableState,
        rightActions = {
            Spacer(modifier = Modifier.width(4.dp))
            CompositionLocalProvider(LocalContentColor provides BrandColor) {
                IconButton(
                    onClick = {
                        animateToDefault()
                        onStartEditing(door)
                    }
                ) {
                    Icon(imageVector = Icons.Outlined.ModeEdit, contentDescription = null)
                }
            }
            CompositionLocalProvider(LocalContentColor provides StarColor) {
                IconButton(
                    onClick = {
                        animateToDefault()
                        onUpdateFavorites(door, !door.favorites)
                    },
                ) {
                    Icon(imageVector = Icons.Outlined.StarOutline, contentDescription = null)
                }
            }
        }
    ) {
        Card {
            Column {
                door.snapshot?.let { snapshot ->
                    AsyncImage(
                        model = snapshot,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = door.name,
                        fontSize = 17.sp,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    )

                    CompositionLocalProvider(LocalContentColor provides BrandColor) {
                        IconButton(
                            onClick = {
                                onUpdateLocked(door, !door.locked)
                            }
                        ) {
                            Icon(
                                imageVector = if (door.locked) {
                                    Icons.Outlined.Lock
                                } else {
                                    Icons.Outlined.LockOpen
                                },
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}