package com.github.mkoshikov.homecamera.model.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.github.mkoshikov.homecamera.model.support.BaseViewModel
import com.github.mkoshikov.homecamera.repository.DoorRepository
import com.github.mkoshikov.homecamera.repository.`object`.Door
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DoorsViewModel(
    private val doorRepository: DoorRepository
) : BaseViewModel() {
    var editing by mutableStateOf<Door?>(null)

    val doors = doorRepository
        .allDoorsAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    init {
        load()
    }

    fun refresh() {
        viewModelScope.launch {
            refreshing = true
            loadData()
            refreshing = false
        }
    }

    private fun load() {
        viewModelScope.launch {
            loading = true
            loadData()
            loading = false
        }
    }

    private suspend fun loadData() {
        runCatching {
            doorRepository.refreshDoors()
        }.onFailure {
            error = it
        }
    }

    fun updateDoorName(door: Door, name: String) {
        doorRepository.updateDoorName(door, name)
    }

    fun updateDoorFavorites(door: Door, favorites: Boolean) {
        doorRepository.updateDoorFavorites(door, favorites)
    }

    fun updateDoorLocked(door: Door, locked: Boolean) {
        doorRepository.updateDoorLocked(door, locked)
    }
}