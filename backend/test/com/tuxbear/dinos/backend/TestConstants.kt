package com.tuxbear.dinos.backend

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent

val jwtToken = "eyJraWQiOiJ0ZXRxRFpyZ05XNTV2dFZJdGFwOGgxNWZ4eWhzbmwzQ1JaWVNZbkhJbEJRPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIzMWIyOWU4NS0zNDZmLTQ4NWItOWM3My0xYjVlNmRiNTE5MTkiLCJhdWQiOiI3YmFmYXNpMTJjdDEwc2V2MmlpamZoM2syYiIsImNvZ25pdG86Z3JvdXBzIjpbInBsYXllcnMiXSwiZXZlbnRfaWQiOiJlMTJhNTBiMC03NzNkLTRhZDItODEzYS01ZDlhZDJmMGNiM2YiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTU3NjMyNDQ0MywiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLmV1LWNlbnRyYWwtMS5hbWF6b25hd3MuY29tXC9ldS1jZW50cmFsLTFfTXU3emVXd2hsIiwiY29nbml0bzp1c2VybmFtZSI6Imppeno2NjYiLCJleHAiOjE1NzYzMjgwNDMsImlhdCI6MTU3NjMyNDQ0M30.CubRzMEN9rM9BtCpXZCkMJ8_11VVynvYdUn0bO27DXtXCYIlURDO3XGhPjkIkE9g9Bn2BxmLQZ7Wmh3y4HnwHg8y8irHotbkddMjx3BIIj783ih30FrjxWObqYWOLgfox5bUmzpSK-miBkygsJ1SBLa6eTlGumT8swl6pkZZsx71dahw57tPDiaMkQdivqByMb1incItaEHbrZUCuVBUUu4d1cf8hP9zrxtOz3_yzIyi95OcIgxe-rQWOJ9hVki5dtoop3tuuC3yYyjpklpzv5xNE_xQZBtk-zbHoPCO3Y2Mhk2NwtewiayVNDe6Ek44aonFqT5-XoElCWDFnJO3hg"

fun authorizedRequest() : APIGatewayProxyRequestEvent {
    return APIGatewayProxyRequestEvent()
            .withHeaders(mapOf(Pair("Authorization", jwtToken)))
}