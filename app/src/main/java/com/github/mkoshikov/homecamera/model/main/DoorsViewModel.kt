package com.github.mkoshikov.homecamera.model.main

import androidx.lifecycle.viewModelScope
import com.github.mkoshikov.homecamera.model.support.BaseViewModel
import com.github.mkoshikov.homecamera.repository.DoorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DoorsViewModel(
    private val doorRepository: DoorRepository
) : BaseViewModel() {
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
}