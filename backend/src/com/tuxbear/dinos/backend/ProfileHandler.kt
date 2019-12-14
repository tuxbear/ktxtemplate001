package com.tuxbear.dinos.backend

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.tuxbear.dinos.domain.user.Player
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


val gamesTableName = System.getenv("gamesTable")
val playerTableName = System.getenv("playerTable")

var dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
        .withLocale(Locale.ENGLISH)
        .withZone(ZoneId.of("UTC"))


class ProfileHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    val mapper = ObjectMapper().registerModule(KotlinModule())

    val dynamoDbClient = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .build()

    val dynamoDb = DynamoDB(dynamoDbClient)

    val dynamoDbItemMapper = DynamoDBMapper(dynamoDbClient)

    fun mapPlayerToDynamoItem(player: Player) : Item {
        return Item()
                .withPrimaryKey("username", player.username)
                .withString("lastSeen", dateFormatter.format(player.lastSeen?.toInstant()))
    }

    fun mapItemToDynamoDb(item: Item) : Player {
        val player = Player()
        player.username = item.getString("username")
        player.friendIds = item.getStringSet("friendIds")
        player.blocked = item.getStringSet("blocked")
        return player
    }

    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {

        context?.logger?.log(mapper.writeValueAsString(input))
        val username = getUserNameFromToken(input?.headers?.get("Authorization"))

        if(username == null) {
            val response = APIGatewayProxyResponseEvent()
            response.statusCode = 400
            response.body = "No username given"
            return response
        }

        var playerInfo = dynamoDb.getTable(playerTableName).getItem("username", username)

        if (playerInfo == null) {
            val newPlayer = Player()
            newPlayer.username = username
            playerInfo = mapPlayerToDynamoItem(newPlayer)
            dynamoDb.getTable(playerTableName).putItem(playerInfo)
        }

        val player = mapItemToDynamoDb(playerInfo)

        return APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(mapper.writeValueAsString(player))
    }
}