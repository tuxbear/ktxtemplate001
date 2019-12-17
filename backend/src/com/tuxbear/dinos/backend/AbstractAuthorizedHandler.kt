package com.tuxbear.dinos.backend

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent

abstract class AbstractAuthorizedHandler  : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    lateinit var username : String
    override fun handleRequest(input: APIGatewayProxyRequestEvent, context: Context?): APIGatewayProxyResponseEvent {
        val potentialUserName = getUserNameFromToken(input.headers?.get("Authorization"))

        if(potentialUserName == null) {
            return APIGatewayProxyResponseEvent()
                    .withStatusCode(400)
                    .withBody("No username given")
        } else {
            username = potentialUserName
        }

        return handle(input, context)
    }

    abstract fun handle(input: APIGatewayProxyRequestEvent, context: Context?): APIGatewayProxyResponseEvent
}