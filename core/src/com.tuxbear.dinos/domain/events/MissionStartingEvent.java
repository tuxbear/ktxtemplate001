package com.tuxbear.dinos.domain.events;

import com.tuxbear.dinos.domain.game.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 23/12/13 Time: 14:16 To change this template use File | Settings | File
 * Templates.
 */
public class MissionStartingEvent extends GameEvent {

    private Mission mission;

    public MissionStartingEvent(Mission mission) {
        this.mission = mission;
    }

    public Mission getMission() {
        return mission;
    }
}
