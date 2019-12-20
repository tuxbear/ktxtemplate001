package com.tuxbear.dinos.backend

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.tuxbear.dinos.backend.dynamodb.MultiplayerGameDao
import com.tuxbear.dinos.backend.dynamodb.PlayerDao
import com.tuxbear.dinos.backend.dynamodb.createDynamoClient
import com.tuxbear.dinos.backend.dynamodb.jsonMapper
import com.tuxbear.dinos.domain.dto.requests.NewGameRequest
import com.tuxbear.dinos.domain.game.ArcadeGameScoreService
import com.tuxbear.dinos.domain.game.GameGenerator
import com.tuxbear.dinos.domain.game.MissionResult
import com.tuxbear.dinos.domain.game.MultiplayerGame

val mapper = ObjectMapper()

class NewGameHandler : AbstractAuthorizedHandler {

    private val playerDao : PlayerDao
    private val gameDao : MultiplayerGameDao

    constructor() : this(createDynamoClient())

    constructor(dynamoClient: AmazonDynamoDB) {
        playerDao = PlayerDao(dynamoClient)
        gameDao = MultiplayerGameDao(dynamoClient)
    }

    override fun handle(input: APIGatewayProxyRequestEvent, context: Context?): APIGatewayProxyResponseEvent {
        val newGameRequest = mapper.readValue(input.body, NewGameRequest::class.java)

        context?.logger?.log(input.body)

        val game = GameGenerator.initGame(newGameRequest.rounds, 15)
        game.difficulty = newGameRequest.difficulty
        game.owner = username

        game.players.addAll(newGameRequest.selectedFriends)
        game.players.add(game.owner)

        gameDao.tableMapper.save(game)

        playerDao.addGameIdToPlayers(game.players, game.id)

        val response = APIGatewayProxyResponseEvent()
        response.statusCode = 200
        response.body = mapper.writeValueAsString(game)
        return response
    }
}

class GetActiveGamesHandler : AbstractAuthorizedHandler {

    private val playerDao : PlayerDao
    private val gameDao : MultiplayerGameDao

    constructor() : this(createDynamoClient())

    constructor(dynamoClient: AmazonDynamoDB) {
        playerDao = PlayerDao(dynamoClient)
        gameDao = MultiplayerGameDao(dynamoClient)
    }


    override fun handle(input: APIGatewayProxyRequestEvent, context: Context?): APIGatewayProxyResponseEvent {
        val gameIds = playerDao.getPlayerByUsername(username)?.activeGames?.map { gid ->
            val game = MultiplayerGame()
            game.id = gid
            game
        }

        val games = gameDao.tableMapper.batchLoad(gameIds)

        return APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(jsonMapper.writeValueAsString(games))
    }
}

class GetGameHandler : AbstractAuthorizedHandler {

    private val playerDao : PlayerDao
    private val gameDao : MultiplayerGameDao

    constructor() : this(createDynamoClient())

    constructor(dynamoClient: AmazonDynamoDB) {
        playerDao = PlayerDao(dynamoClient)
        gameDao = MultiplayerGameDao(dynamoClient)
    }


    override fun handle(input: APIGatewayProxyRequestEvent, context: Context?): APIGatewayProxyResponseEvent {
        val gameId = input.queryStringParameters["gameId"] ?: ""
        val isInGame = playerDao.getPlayerByUsername(username)?.activeGames?.contains(gameId) ?: false

        return if(isInGame) {
            val game = gameDao.tableMapper.load(gameId)
            APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(jsonMapper.writeValueAsString(game))
        } else {
            APIGatewayProxyResponseEvent().withStatusCode(404)
        }
    }
}


class ReportMissionResultHandler : AbstractAuthorizedHandler {

    private val playerDao : PlayerDao
    private val gameDao : MultiplayerGameDao

    constructor() : this(createDynamoClient())

    constructor(dynamoClient: AmazonDynamoDB) {
        playerDao = PlayerDao(dynamoClient)
        gameDao = MultiplayerGameDao(dynamoClient)
    }


    override fun handle(input: APIGatewayProxyRequestEvent, context: Context?): APIGatewayProxyResponseEvent {
        val result = mapper.readValue<MissionResult>(input.body)

        val isInGame = playerDao.getPlayerByUsername(username)?.activeGames?.contains(result.gameId) ?: false

        return if(isInGame) {
            result.playerId = username
            val game = gameDao.tableMapper.load(result.gameId)

            val existingEntry = game.missionResults.find { mr -> mr.gameId == result.gameId && mr.playerId == username && result.missionId == result.missionId }

            if (existingEntry != null) {
                return APIGatewayProxyResponseEvent().withStatusCode(400)
            }

            result.score = ArcadeGameScoreService().getScore(result)
            game.reportResultForCurrentRound(result)
            game.state = game.calculateGlobalGameState()

            gameDao.tableMapper.save(game)

            APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(mapper.writeValueAsString(game))
        } else {
            APIGatewayProxyResponseEvent().withStatusCode(404)
        }
    }
}

