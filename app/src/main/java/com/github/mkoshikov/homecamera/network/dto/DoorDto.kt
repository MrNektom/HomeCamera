package com.github.mkoshikov.homecamera.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class DoorDto(
    val id: Long,
    val name: String,
    val room: String?,
    val favorites: Boolean,
    val snapshot: String? = null
)
