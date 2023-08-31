package com.github.mkoshikov.homecamera.network.dto.response

import com.github.mkoshikov.homecamera.network.dto.CameraDto
import kotlinx.serialization.Serializable

@Serializable
data class CamerasResponse(
    val room: List<String>,
    val cameras: List<CameraDto>
)
