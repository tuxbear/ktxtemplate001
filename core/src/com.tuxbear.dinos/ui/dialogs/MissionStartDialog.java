package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.ui.widgets.board.DinoActor;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/01/14 Time: 16:21 To change this template use File | Settings | File
 * Templates.
 */
public class MissionStartDialog extends AbstractCallbackDialog {
    public MissionStartDialog(MultiplayerGame game, Skin skin) {
        super("Round " + game.getCurrentMissionNumber() + " of " + game.getNumberOfMissions(), skin);

        int missionDinoNumber = game.getCurrentMission().getPieceNumber();
        Texture dinoTexture = DinoActor.getDinoTextureFromNumber(missionDinoNumber);
        Image missionDino = new Image(dinoTexture);

        getContentTable().add(missionDino).fill();

        button("Game List", "game list");
        button("Table", "score list");
        button("Play!", "play");

        invalidateHierarchy();
    }
}
