package com.github.mkoshikov.homecamera.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

enum class SwipeState {
    DEFAULT,
    RIGHT
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToAction(
    state: SwipeableState<SwipeState> = rememberSwipeableState(initialValue = SwipeState.DEFAULT),
    rightActions: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    BoxWithConstraints {
        var rightActionsWidth by rememberSaveable {
            mutableStateOf(constraints.maxWidth.toFloat())
        }

        val anchors = mapOf(
            0f to SwipeState.DEFAULT,
            -rightActionsWidth to SwipeState.RIGHT
        )

        if (rightActions != null) Row(
            modifier = Modifier
                .alpha(
                    animateFloatAsState(
                        if (state.targetValue == SwipeState.RIGHT) 1f else 0f,
                        label = ""
                    ).value
                )
                .align(Alignment.CenterEnd)
                .onSizeChanged { rightActionsWidth = it.width.toFloat() },
            content = rightActions
        ) else {
            rightActionsWidth = 0f
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(state.offset.value.roundToInt(), 0) }
                .swipeable(
                    state = state,
                    anchors = anchors,
                    orientation = Orientation.Horizontal
                ),
            content = content
        )
    }
}