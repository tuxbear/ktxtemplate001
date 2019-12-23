package com.tuxbear.dinos.domain.game

enum class LocalGameState {
    YOU_CAN_PLAY, WAITING_JUST_FOR_YOU, WAITING_FOR_OPPONENTS, ENDED;

    fun prettyString(): String {
        return when (this) {
            YOU_CAN_PLAY -> "Game on!"
            WAITING_JUST_FOR_YOU -> "Everyone's waiting!"
            WAITING_FOR_OPPONENTS -> "Waiting"
            ENDED -> "Ended!"
        }
    }
}