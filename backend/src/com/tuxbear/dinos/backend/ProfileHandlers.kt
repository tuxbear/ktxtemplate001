package com.tuxbear.dinos.backend

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType
import com.amazonaws.services.cognitoidp.model.UsernameExistsException
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.module.kotlin.readValue
import com.tuxbear.dinos.backend.dynamodb.PlayerDao
import com.tuxbear.dinos.backend.dynamodb.createDynamoClient
import com.tuxbear.dinos.backend.dynamodb.jsonMapper
import com.tuxbear.dinos.backend.integrations.CognitoClient
import com.tuxbear.dinos.domain.dto.requests.LoginOrRegisterRequest
import com.tuxbear.dinos.domain.user.CognitoTokens
import com.tuxbear.dinos.domain.user.Player


class ProfileHandler : AbstractAuthorizedHandler {

    private val playerDao: PlayerDao

    constructor() : this(createDynamoClient())

    constructor(dynamoClient: AmazonDynamoDB) {
        playerDao = PlayerDao(dynamoClient)
    }

    override fun handle(input: APIGatewayProxyRequestEvent, context: Context?): APIGatewayProxyResponseEvent {
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

class LoginRegisterHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private val playerDao : PlayerDao

    constructor() : this(createDynamoClient())

    constructor(dynamoClient: AmazonDynamoDB) {
        playerDao = PlayerDao(dynamoClient)
    }

    override fun handleRequest(input: APIGatewayProxyRequestEvent, context: Context?): APIGatewayProxyResponseEvent {
        val auth = mapper.readValue<LoginOrRegisterRequest>(input.body)

        val cognitoClient = CognitoClient()
        val answer: AuthenticationResultType?

        answer = try {
            cognitoClient.register(auth.username, auth.password)
            cognitoClient.login(auth.username, auth.password)
        } catch (exists: UsernameExistsException) {
            cognitoClient.login(auth.username, auth.password)
        }

        if (answer == null) {
            return APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
        }

        val tokens = CognitoTokens()
        tokens.accessToken = answer.accessToken
        tokens.idToken = answer.idToken
        tokens.refreshToken = answer.refreshToken

        return APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(mapper.writeValueAsString(tokens))

    }
}

class RefreshTokenHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private val playerDao : PlayerDao

    constructor() : this(createDynamoClient())

    constructor(dynamoClient: AmazonDynamoDB) {
        playerDao = PlayerDao(dynamoClient)
    }

    override fun handleRequest(input: APIGatewayProxyRequestEvent, context: Context?): APIGatewayProxyResponseEvent {
        val auth = mapper.readValue<CognitoTokens>(input.body)

        val cognitoClient = CognitoClient()
        val answer = cognitoClient.refreshAccessToken(auth.refreshToken);

        if (answer == null) {
            return APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
        }

        val tokens = CognitoTokens()
        tokens.accessToken = answer.accessToken
        tokens.idToken = answer.idToken
        tokens.refreshToken = answer.refreshToken

        return APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(mapper.writeValueAsString(tokens))

    }
}


