package com.github.mkoshikov.homecamera.repository

import com.github.mkoshikov.homecamera.network.Api
import com.github.mkoshikov.homecamera.repository.`object`.Camera
import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.find
import kotlinx.coroutines.flow.map

class CameraRepository(
    private val realm: Realm,
    private val api: Api
) {

    @Throws(Exception::class)
    suspend fun refreshCameras() {
        api.getCameras()
            .data.cameras
            .also {
                realm.writeBlocking {
                    delete<Camera>()
                    it.forEach { camera ->
                        copyToRealm(camera.toRealmCamera())
                    }
                }
            }
    }

    fun hasSavedCameras() = realm
        .query<Camera>()
        .count()
        .find { it > 0 }

    fun allCamerasAsFlow() = realm
        .query<Camera>()
        .find()
        .asFlow()
        .map { it.list }

    fun updateCameraFavorites(camera: Camera, favorites: Boolean) {
        realm.writeBlocking {
            findLatest(camera)?.favorites = favorites
        }
    }
}