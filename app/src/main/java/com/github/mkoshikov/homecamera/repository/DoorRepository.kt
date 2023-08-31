package com.github.mkoshikov.homecamera.repository

import com.github.mkoshikov.homecamera.network.Api
import com.github.mkoshikov.homecamera.repository.`object`.Door
import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.map
import kotlin.jvm.Throws


class DoorRepository(
    private val realm: Realm,
    private val api: Api
) {
    @Throws(Exception::class)
    suspend fun getDoors() = api.getDoors()

    @Throws(Exception::class)
    suspend fun refreshDoors() {
        getDoors().data
            .let { doors ->
                realm.writeBlocking {
                    delete<Door>()

                    doors.forEach { door ->
                        copyToRealm(door.toRealmDoor())
                    }
                }
            }
    }

    fun allDoorsAsFlow() = realm
        .query<Door>()
        .find()
        .asFlow()
        .map { it.list }
}