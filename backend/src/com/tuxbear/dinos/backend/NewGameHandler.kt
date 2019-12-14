package com.tuxbear.dinos.backend

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.tuxbear.dinos.services.impl.aws.requests.NewGameRequest

val mapper = ObjectMapper().registerModule(KotlinModule())

class NewGameHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {

        val newGameRequest = mapper.readValue(input?.body, NewGameRequest::class.java)

        context?.logger?.log(input?.body)

        val apiGatewayProxyResponseEvent = APIGatewayProxyResponseEvent()
        apiGatewayProxyResponseEvent.statusCode = 200
        apiGatewayProxyResponseEvent.body = ""

        return apiGatewayProxyResponseEvent
    }
}


class GetActiveGamesHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {

        val response = APIGatewayProxyResponseEvent()
        val username = input?.requestContext?.identity?.cognitoIdentityId

        if(username == null) {
            response.statusCode = 400
            response.body = "no username found"
        } else {
            // dynamo DB get users active game IDs
            // BatchGetItem all active games and return them
        }

        return response
    }
}