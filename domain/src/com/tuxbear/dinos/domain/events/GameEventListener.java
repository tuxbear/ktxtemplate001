package com.tuxbear.dinos.domain.events;

public interface GameEventListener {

    void processEvent(GameEvent event);
}
