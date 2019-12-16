package com.tuxbear.dinos.backend

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.tuxbear.dinos.backend.dynamodb.PlayerDao
import com.tuxbear.dinos.backend.dynamodb.createDynamoClient
import com.tuxbear.dinos.backend.dynamodb.jsonMapper
import com.tuxbear.dinos.domain.user.Player


class ProfileHandler : AbstractAuthorizedHandler {

    private val playerDao : PlayerDao

    constructor() : this(createDynamoClient())

    constructor(dynamoClient: AmazonDynamoDB) {
        playerDao = PlayerDao(dynamoClient)
    }

    override fun handle(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        var player = playerDao.getPlayerByUsername(username)

        if (player == null) {
            player = Player()
            player.username = username
            playerDao.tableMapper.save(player)
        }

        return APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(jsonMapper.writeValueAsString(player))
    }
}

