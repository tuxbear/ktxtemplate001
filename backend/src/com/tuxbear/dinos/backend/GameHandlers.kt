package com.tuxbear.dinos.backend

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.tuxbear.dinos.backend.dynamodb.MultiplayerGameDao
import com.tuxbear.dinos.backend.dynamodb.PlayerDao
import com.tuxbear.dinos.backend.dynamodb.createDynamoClient
import com.tuxbear.dinos.backend.dynamodb.jsonMapper
import com.tuxbear.dinos.domain.dto.requests.NewGameRequest
import com.tuxbear.dinos.domain.game.GameGenerator
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

    override fun handle(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        val newGameRequest = mapper.readValue(input?.body, NewGameRequest::class.java)

        context?.logger?.log(input?.body)

        val game = GameGenerator.initGame(10, 15)
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


    override fun handle(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
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