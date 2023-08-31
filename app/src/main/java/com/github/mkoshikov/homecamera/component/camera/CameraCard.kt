package com.github.mkoshikov.homecamera.component.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.mkoshikov.homecamera.component.SwipeState
import com.github.mkoshikov.homecamera.component.SwipeToAction
import com.github.mkoshikov.homecamera.repository.`object`.Camera
import kotlinx.coroutines.launch

val StarColor = Color(224, 190, 53)
val RecColor = Color(250, 48, 48)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CameraCard(
    camera: Camera,
    onChangeFavorites: (Boolean) -> Unit
) {

    val swipeState = rememberSwipeableState(initialValue = SwipeState.DEFAULT)
    val scope = rememberCoroutineScope()
    SwipeToAction(
        state = swipeState,
        rightActions = {
            IconButton(
                onClick = {
                    scope.launch {
                        onChangeFavorites(!camera.favorites)
                        swipeState.animateTo(SwipeState.DEFAULT)
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.StarOutline,
                    contentDescription = null,
                    tint = StarColor
                )
            }
        }
    ) {
        Card {
            Column {
                AsyncImage(
                    model = camera.snapshot,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = camera.name,
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 17.sp,

                    )
            }
        }

        if (camera.favorites) Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = null,
            tint = StarColor,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.TopEnd)
        )

        if (camera.rec) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(RecColor),
                )
                Text(
                    text = "REC",
                    fontSize = 11.sp,
                    lineHeight = 11.79.sp,
                    color = RecColor,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
        }
    }
}