package com.github.mkoshikov.homecamera.network

import com.github.mkoshikov.homecamera.network.dto.response.ApiResponse
import com.github.mkoshikov.homecamera.network.dto.response.CamerasResponse
import com.github.mkoshikov.homecamera.network.dto.DoorDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class Api(
    private val httpClient: HttpClient
) {
    @Throws(Exception::class)
    suspend fun getCameras() = httpClient
        .get("cameras")
        .body<ApiResponse<CamerasResponse>>()

    @Throws(Exception::class)
    suspend fun getDoors() = httpClient
        .get("doors")
        .body<ApiResponse<List<DoorDto>>>()
}