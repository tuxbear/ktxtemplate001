package com.tuxbear.dinos.domain.events;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 21/12/13 Time: 16:09 To change this template use File | Settings | File
 * Templates.
 */
public interface GameEventListener {

    void processEvent(GameEvent event);

}
