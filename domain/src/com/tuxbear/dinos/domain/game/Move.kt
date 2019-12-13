package com.tuxbear.dinos.domain.game

data class Move(
        /**
         * The timestamp in system time for when the move was made
         */
        val timestamp: Long,
        val pieceNumber: Int,
        val direction: Direction
)