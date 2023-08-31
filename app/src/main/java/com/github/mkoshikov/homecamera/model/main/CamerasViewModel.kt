package com.github.mkoshikov.homecamera.model.main

import androidx.lifecycle.viewModelScope
import com.github.mkoshikov.homecamera.model.support.BaseViewModel
import com.github.mkoshikov.homecamera.repository.CameraRepository
import com.github.mkoshikov.homecamera.repository.`object`.Camera
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CamerasViewModel(
    private val cameraRepository: CameraRepository
) : BaseViewModel() {

    val savedCameras = cameraRepository
        .allCamerasAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        if (!cameraRepository.hasSavedCameras()) {
            load()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            refreshing = true
            loadData()
            refreshing = false
        }
    }

    fun load() {
        viewModelScope.launch {
            loading = true
            loadData()
            loading = false
        }
    }

    suspend fun loadData() {
        runCatching {
            cameraRepository.refreshCameras()
        }.onFailure {
            error = it
        }
    }

    fun onChangeFavorites(camera: Camera, favorites: Boolean) {
        cameraRepository.updateCameraFavorites(camera, favorites)
    }
}