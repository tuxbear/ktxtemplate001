package com.tuxbear.dinos.domain.events

import com.tuxbear.dinos.domain.game.Move

data class DinoMovedEvent(var move: Move) : GameEvent()