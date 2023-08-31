package com.github.mkoshikov.homecamera.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T
)
