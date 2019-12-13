package com.tuxbear.dinos.backend

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule


class ProfileHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    val mapper = ObjectMapper().registerModule(KotlinModule())

    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {

        context?.logger?.log(mapper.writeValueAsString(input))




        val apiGatewayProxyResponseEvent = APIGatewayProxyResponseEvent()
        apiGatewayProxyResponseEvent.statusCode = 200
        apiGatewayProxyResponseEvent.body = mapper.writeValueAsString(input?.headers)

        return apiGatewayProxyResponseEvent
    }
}
