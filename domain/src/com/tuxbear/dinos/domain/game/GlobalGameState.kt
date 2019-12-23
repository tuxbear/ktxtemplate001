package com.tuxbear.dinos.domain.game

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/01/14 Time: 16:53 To change this template use File | Settings | File
 * Templates.
 */
enum class GlobalGameState {
    ACTIVE, ABORTED, ENDED;

    fun getText(): String {
        return when (this) {
            ACTIVE -> "Game on"
            ABORTED -> "Aborted"
            ENDED -> "Completed"
            else -> "?"
        }
    }
}