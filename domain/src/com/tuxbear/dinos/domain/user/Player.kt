package com.tuxbear.dinos.domain.user

import java.util.*
import kotlin.collections.HashSet

class Player {
    var username: String? = null
    var lastSeen: Date? = null
    var friendIds: Set<String>? = null
    var blocked: Set<String>? = null

    init {
        blocked = HashSet()
        friendIds = HashSet()
        lastSeen = Date()
    }
}