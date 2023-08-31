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
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.mkoshikov.homecamera.component.SwipeToAction
import com.github.mkoshikov.homecamera.component.camera.StarColor
import com.github.mkoshikov.homecamera.repository.`object`.Door
import com.github.mkoshikov.homecamera.ui.theme.BrandColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoorCard(door: Door) {
    SwipeToAction(
        rightActions = {
            Spacer(modifier = Modifier.width(4.dp))
            CompositionLocalProvider(LocalContentColor provides BrandColor) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.ModeEdit, contentDescription = null)
                }
            }
            CompositionLocalProvider(LocalContentColor provides StarColor) {
                IconButton(onClick = { /*TODO*/ }) {
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        contentScale = ContentScale.FillWidth
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
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}