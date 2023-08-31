package com.github.mkoshikov.homecamera.repository.`object`

import io.realm.kotlin.types.RealmObject

class Camera : RealmObject {
    var name: String = ""
    var snapshot: String = ""
    var room: String? = null
    var id: Long = 0
    var favorites: Boolean = false
    var rec: Boolean = false
}