package com.tuxbear.dinos.domain.events;

import java.io.IOException;

public interface GameEventListener {

    void processEvent(GameEvent event) throws IOException;
}
