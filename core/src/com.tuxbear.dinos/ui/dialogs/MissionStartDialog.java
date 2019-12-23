package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.ui.widgets.board.DinoActor;

import java.io.IOException;

public class MissionStartDialog extends AbstractCallbackDialog {
    public MissionStartDialog(MultiplayerGame game, Player currentPlayer, Skin skin) {
        super("Round " + game.getCurrentMissionNumber(currentPlayer.getUsername()) + " of " + game.getNumberOfMissions(), skin);

        int missionDinoNumber = game.getCurrentMission(currentPlayer.getUsername()).getPieceNumber();
        Texture dinoTexture = DinoActor.getDinoTextureFromNumber(missionDinoNumber);
        Image missionDino = new Image(dinoTexture);

        getContentTable().add(missionDino).fill();

        button("Game List", "game list");
        button("Table", "score list");
        button("Play!", "play");

        invalidateHierarchy();
    }
}
