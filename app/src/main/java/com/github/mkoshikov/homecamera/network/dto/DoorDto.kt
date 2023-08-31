package com.github.mkoshikov.homecamera.network.dto

import com.github.mkoshikov.homecamera.repository.`object`.Door
import kotlinx.serialization.Serializable

@Serializable
data class DoorDto(
    val id: Long,
    val name: String,
    val room: String?,
    val favorites: Boolean,
    val snapshot: String? = null
) {
    fun toRealmDoor() = Door().apply {
        val self = this@DoorDto

        id = self.id
        name = self.name
        room = self.room
        favorites = self.favorites
        snapshot = self.snapshot
    }
}
