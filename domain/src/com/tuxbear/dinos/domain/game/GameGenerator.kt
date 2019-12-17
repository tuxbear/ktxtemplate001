package com.tuxbear.dinos.domain.game

import com.tuxbear.dinos.domain.user.Player
import java.util.*

object GameGenerator {

    fun generateRound(number: Int, dino: Int, x: Int, y: Int): Mission {
        val mission = Mission()
        mission.setPieceNumber(dino)
        mission.setPosition(BoardPosition(x, y))
        mission.id = UUID.randomUUID().toString()
        return mission
    }

    fun initGame(rounds: Int, walls: Int): MultiplayerGame {
        val rows = 10
        val columns = 8
        val rand = Random()
        val board = Board.getByName("")
        val multiplayerGame = MultiplayerGame()
        multiplayerGame.created = Date()
        multiplayerGame.board = board
        for (i in 0..3) {
            val x = rand.nextInt(columns)
            val y = rand.nextInt(rows)
            multiplayerGame.initialPiecePositions[i.toString()] = BoardPosition(x, y)
        }
        val missionPos = arrayOf(
                BoardPosition(0, 9),
                BoardPosition(0, 8),
                BoardPosition(2, 9),
                BoardPosition(3, 9),
                BoardPosition(3, 7),
                BoardPosition(5, 9),
                BoardPosition(6, 9),
                BoardPosition(7, 9),
                BoardPosition(7, 7),
                BoardPosition(5, 6),
                BoardPosition(4, 3),
                BoardPosition(7, 3),
                BoardPosition(7, 2),
                BoardPosition(7, 0),
                BoardPosition(5, 0),
                BoardPosition(2, 0),
                BoardPosition(4, 0),
                BoardPosition(1, 0),
                BoardPosition(0, 0),
                BoardPosition(0, 7),
                BoardPosition(0, 2),
                BoardPosition(0, 3),
                BoardPosition(1, 4)
        )
        val missionList = ArrayList<Mission>()
        for (i in missionPos.indices) {
            val dino = rand.nextInt(4)
            val pos = missionPos[i]
            missionList.add(generateRound(i + 1, dino, pos.x, pos.y))
        }
        multiplayerGame.missions = missionList
        multiplayerGame.state = GlobalGameState.ACTIVE
        multiplayerGame.currentMissionNumber = 1
        multiplayerGame.id = UUID.randomUUID().toString()
        return multiplayerGame
    }

}