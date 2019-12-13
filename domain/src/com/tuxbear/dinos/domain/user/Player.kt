package com.tuxbear.dinos.domain.user

import java.util.*

class Player {
    var id: String? = null
    var lastSeen: Date? = null
    var friendIds: List<String>? = null
    var blocked: List<String>? = null

}