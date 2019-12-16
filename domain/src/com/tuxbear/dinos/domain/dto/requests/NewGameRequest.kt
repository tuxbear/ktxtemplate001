package com.tuxbear.dinos.domain.dto.requests

class NewGameRequest {

    lateinit var board: String
    lateinit var selectedFriends: List<String>
    var rounds: Int = 0
    lateinit var difficulty: String

    constructor()

    constructor(board: String, selectedFriends: List<String>, rounds: Int, difficulty: String) {
        this.board = board
        this.selectedFriends = selectedFriends
        this.rounds = rounds
        this.difficulty = difficulty
    }
}