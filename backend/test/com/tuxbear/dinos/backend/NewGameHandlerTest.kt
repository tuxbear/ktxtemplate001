package com.tuxbear.dinos.backend

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.fasterxml.jackson.module.kotlin.readValue
import com.tuxbear.dinos.backend.dynamodb.MultiplayerGameDao
import com.tuxbear.dinos.backend.dynamodb.PlayerDao
import com.tuxbear.dinos.backend.dynamodb.jsonMapper
import com.tuxbear.dinos.domain.dto.requests.NewGameRequest
import com.tuxbear.dinos.domain.game.MultiplayerGame
import com.tuxbear.dinos.domain.user.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class NewGameHandlerTest {

    private lateinit var playerDao: PlayerDao
    private lateinit var gameDao: MultiplayerGameDao
    lateinit var localDb: AmazonDynamoDB

    @BeforeEach
    internal fun setUp() {
        AwsDynamoDbLocalTestUtils.initSqLite()
        localDb = DynamoDBEmbedded.create().amazonDynamoDB()
        gameDao = MultiplayerGameDao(localDb)
        playerDao = PlayerDao(localDb)
        gameDao.tableMapper.createTableIfNotExists(ProvisionedThroughput(1,1))
        playerDao.tableMapper.createTableIfNotExists(ProvisionedThroughput(1,1))

        val eleni = Player()
        eleni.username = "eleni"

        val jizz = Player()
        jizz.username = "jizz666"

        playerDao.tableMapper.save(eleni)
        playerDao.tableMapper.save(jizz)
    }

    @Test
    internal fun createNewGameTest() {
        val newGameRequest = NewGameRequest(
                "{valley}", emptyList(), 5, "{easy}")

        val context = null
        val authorizedRequest = authorizedRequest()
                .withBody(jsonMapper.writeValueAsString(newGameRequest))

        NewGameHandler(localDb).handleRequest(authorizedRequest, context)

        val allGames = gameDao.tableMapper.scan(DynamoDBScanExpression())
        assert(allGames.size == 1)

        val response = GetActiveGamesHandler(localDb).handleRequest(authorizedRequest, context)

        val games = mapper.readValue<List<MultiplayerGame>>(response.body)

        assert(games.size == 1)
    }
}