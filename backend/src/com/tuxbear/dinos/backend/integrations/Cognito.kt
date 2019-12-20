package com.tuxbear.dinos.backend.integrations

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder
import com.amazonaws.services.cognitoidp.model.*


class CognitoClient () {

    val cognitoAppClientId = "7bafasi12ct10sev2iijfh3k2b"

    fun login(username:String, password: String): AuthenticationResultType? {

        val request = InitiateAuthRequest()
        request.setAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
        request.clientId = cognitoAppClientId

        request.addAuthParametersEntry("USERNAME", username)
        request.addAuthParametersEntry("PASSWORD", password)

        val result = createClient().initiateAuth(request)

        return result?.authenticationResult
    }

    fun register(username:String, password: String): SignUpResult? {


        val signUpRequest = SignUpRequest()
        signUpRequest.clientId = cognitoAppClientId
        signUpRequest.username = username
        signUpRequest.password = password

        return createClient().signUp(signUpRequest)
    }

    fun refreshAccessToken(refreshToken : String) : AuthenticationResultType? {
        val request = InitiateAuthRequest()
                .withClientId(cognitoAppClientId)
                .withAuthFlow(AuthFlowType.REFRESH_TOKEN_AUTH)
                .addAuthParametersEntry("REFRESH_TOKEN", refreshToken);

        return createClient().initiateAuth(request).authenticationResult
    }

    private fun createClient(): AWSCognitoIdentityProvider {
        return AWSCognitoIdentityProviderClientBuilder
                .standard()
                .withCredentials(AWSStaticCredentialsProvider(AnonymousAWSCredentials()))
                .withRegion("eu-central-1")
                .build()
    }
}