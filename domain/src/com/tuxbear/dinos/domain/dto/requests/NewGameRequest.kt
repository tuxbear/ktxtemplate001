package com.tuxbear.dinos.services.impl.aws.requests

data class NewGameRequest(
        var username: String,
        var board: String,
        var selectedFriends: List<String>,
        var rounds: Int,
        var difficulty: String
)