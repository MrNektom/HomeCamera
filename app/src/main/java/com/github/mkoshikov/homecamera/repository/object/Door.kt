package com.github.mkoshikov.homecamera.repository.`object`

import io.realm.kotlin.types.RealmObject

class Door : RealmObject {
    var id: Long = 0
    var name: String = ""
    var room: String? = null
    var favorites: Boolean = false
    var snapshot: String? = null
}