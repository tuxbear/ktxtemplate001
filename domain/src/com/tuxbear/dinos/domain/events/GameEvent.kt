package com.tuxbear.dinos.domain.events

abstract class GameEvent {
    val simpleEventName: String
        get() = this.javaClass.simpleName
}