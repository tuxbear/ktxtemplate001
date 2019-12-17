package com.tuxbear.dinos.backend

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.tuxbear.dinos.backend.dynamodb.createDynamoDbMapper
import com.tuxbear.dinos.domain.user.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class ProfileHandlerTest {

    lateinit var mapper : DynamoDBMapper
    lateinit var typedMapper : DynamoDBTableMapper<Player, String, Date>
    lateinit var localDb: AmazonDynamoDB

    @BeforeEach
    internal fun setUp() {
        AwsDynamoDbLocalTestUtils.initSqLite()
        localDb = DynamoDBEmbedded.create().amazonDynamoDB()
        mapper = createDynamoDbMapper(localDb)
        typedMapper = mapper.newTableMapper<Player, String, Date>(Player::class.java)
        typedMapper
                .createTableIfNotExists(ProvisionedThroughput(10,10))
    }

    @Test
    internal fun createPlayerTest() {
        val context = null
        val request = authorizedRequest()

        ProfileHandler(localDb).handleRequest(request, context)

        assert(typedMapper.scan(DynamoDBScanExpression())[0].username == "jizz666")

        ProfileHandler(localDb).handleRequest(request, context)
    }
}