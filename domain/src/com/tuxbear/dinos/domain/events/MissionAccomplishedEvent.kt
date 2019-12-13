package com.tuxbear.dinos.domain.events

import com.tuxbear.dinos.domain.game.MissionResult

class MissionAccomplishedEvent(val result: MissionResult) : GameEvent()