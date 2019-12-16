package com.tuxbear.dinos.backend.dynamodb

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.fasterxml.jackson.databind.ObjectMapper
import com.tuxbear.dinos.domain.game.MultiplayerGame
import com.tuxbear.dinos.domain.user.Player


val jsonMapper : ObjectMapper = ObjectMapper()

val gamesTableName : String = System.getenv("gamesTable") ?: "gamesTable"
val playerTableName : String = System.getenv("playerTable") ?: "playerTable"


abstract class DynamoDbDao<T, H, R>(clazz : Class<T>, val db : AmazonDynamoDB) {
    protected val mapper = createDynamoDbMapper(db)
    val tableMapper = mapper.newTableMapper<T, H, R>(clazz)
}


fun createDynamoDbMapper(db: AmazonDynamoDB): DynamoDBMapper {
    val dynamoMapperConfig = DynamoDBMapperConfig.builder()
            .withTableNameResolver(EnvironmentResolver)
            .withConversionSchema(ConversionSchemas.V2)
            .build()

    return DynamoDBMapper(db, dynamoMapperConfig)
}

fun createDynamoClient(): AmazonDynamoDB {
    return AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .build()
}

object EnvironmentResolver : DynamoDBMapperConfig.TableNameResolver {
    override fun getTableName(clazz: Class<*>?, config: DynamoDBMapperConfig?): String {
        return when(clazz) {
            MultiplayerGame::class.java -> gamesTableName
            Player::class.java -> playerTableName
            else -> ""
        }
    }
}