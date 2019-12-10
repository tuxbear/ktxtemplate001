package com.tuxbear.dinos.domain.game;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/01/14 Time: 17:02 To change this template use File | Settings | File
 * Templates.
 */
public enum LocalGameState {
    YOU_CAN_PLAY,
    WAITING_JUST_FOR_YOU,
    WAITING_FOR_OPPONENTS,
    ENDED;


    public String prettyString() {
        switch (this) {
            case YOU_CAN_PLAY:
                return "Your turn";
            case WAITING_JUST_FOR_YOU:
                return "Everyone's waiting!";
            case WAITING_FOR_OPPONENTS:
                return "Waiting";
            case ENDED:
                return "Ended!";
        }

        return "";
    }
}
