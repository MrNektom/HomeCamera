package com.github.mkoshikov.homecamera.network.dto

import com.github.mkoshikov.homecamera.repository.`object`.Camera
import kotlinx.serialization.Serializable

@Serializable
data class CameraDto(
    var id: Long,
    var name: String,
    var room: String?,
    var favorites: Boolean,
    var rec: Boolean,
    var snapshot: String,
) {
    fun toRealmCamera(): Camera {
        val self = this
        return Camera().apply {
            id = self.id
            name = self.name
            room = self.room
            favorites = self.favorites
            rec = self.rec
            snapshot = self.snapshot
        }
    }
}
