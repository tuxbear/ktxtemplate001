package com.tuxbear.dinos.backend

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT

fun getUserNameFromToken(token: String?) : String? {
    return try {
        val jwt: DecodedJWT = JWT.decode(token)
        return jwt.getClaim("cognito:username").asString()
    } catch (exception: Exception) {
        null
    }
}