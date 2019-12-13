package com.tuxbear.dinos.domain.events

import com.tuxbear.dinos.domain.game.Mission

class MissionStartingEvent(val mission: Mission) : GameEvent()