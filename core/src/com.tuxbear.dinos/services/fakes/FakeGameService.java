package com.tuxbear.dinos.services.fakes;

import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.*;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.responses.GameEventUpdatesResponse;

import java.util.*;

public class FakeGameService implements GameService {
    @Override
    public void createGameAsync(String username, List<String> players, String board, int rounds, String difficulty,
                                final ServerCallback<MultiplayerGame> responseCallback) {

        responseCallback.processResult(generateGame_DELETE_ME(rounds, 25), new ServerCallResults(ServerCallStatus.SUCCESS, ""));
    }

    @Override
    public void getGameByIdAsync(String id, ServerCallback<MultiplayerGame> responseCallback) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getActiveGamesForPlayerAsync(Player player, ServerCallback<List> responseCallback) {

        responseCallback.processResult( Arrays.asList(
                generateGame_DELETE_ME(5,15),
                generateGame_DELETE_ME(7,15),
                generateGame_DELETE_ME(3,15),
                generateGame_DELETE_ME(6,15),
                generateGame_DELETE_ME(5,15),
                generateGame_DELETE_ME(10,15),
                generateGame_DELETE_ME(21,15)),
                new ServerCallResults(ServerCallStatus.SUCCESS, ""));

    }

    @Override
    public void reportRoundResultsAsync(MissionResult result, ServerCallback<MultiplayerGame> responseCallback) {
    }

    @Override
    public void getUpdatesAsync(Date since, ServerCallback<GameEventUpdatesResponse> responseCallback) {

    }

    private Mission generateRound_DELETE_ME(int number, int dino, int x, int y) {

        Mission mission = new Mission();
        mission.setPieceNumber(dino);
        mission.setPosition(new BoardPosition(x, y));
        mission.setId(UUID.randomUUID().toString());

        return mission;
    }

    private MultiplayerGame generateGame_DELETE_ME(int rounds, int walls) {
        int rows = 10;
        int columns = 8;

        Random rand = new Random();

        Board board = Board.getByName("");
        Player me = new Player();
        me.setId("eleni");

        MultiplayerGame multiplayerGame = new MultiplayerGame();
        multiplayerGame.getPlayers().add(me);

        multiplayerGame.setBoard(board);
        for(int i = 0; i < 4; i++){
            int x = rand.nextInt(columns);
            int y = rand.nextInt(rows);

            multiplayerGame.getInitialPiecePositions().put(i, new BoardPosition(x, y));
        }

        BoardPosition[] missionPos = new BoardPosition[] {
                new BoardPosition(0,9),
                new BoardPosition(0,8),
                new BoardPosition(2,9),
                new BoardPosition(3,9),
                new BoardPosition(3,7),
                new BoardPosition(5,9),
                new BoardPosition(6,9),
                new BoardPosition(7,9),
                new BoardPosition(7,7),
                new BoardPosition(5,6),
                new BoardPosition(4,3),
                new BoardPosition(7,3),
                new BoardPosition(7,2),
                new BoardPosition(7,0),
                new BoardPosition(5,0),
                new BoardPosition(2,0),
                new BoardPosition(4,0),
                new BoardPosition(1,0),
                new BoardPosition(0,0),
                new BoardPosition(0,7),
                new BoardPosition(0,2),
                new BoardPosition(0,3),
                new BoardPosition(1,4)
        };
        ArrayList<Mission> missionList = new ArrayList<>();
        for(int i = 0; i < missionPos.length; i++){
            int dino = rand.nextInt(4);
            BoardPosition pos = missionPos[i];

            missionList.add(generateRound_DELETE_ME(i + 1, dino, pos.x, pos.y));
        }


        multiplayerGame.setMissions(missionList);
        multiplayerGame.setCurrentMissionNumber(1);
        multiplayerGame.setId(UUID.randomUUID().toString());

        return multiplayerGame;
    }


}