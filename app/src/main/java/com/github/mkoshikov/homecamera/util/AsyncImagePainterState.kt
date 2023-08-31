package com.github.mkoshikov.homecamera.util

import coil.compose.AsyncImagePainter

fun AsyncImagePainter.State.isSuccess() = this is AsyncImagePainter.State.Success