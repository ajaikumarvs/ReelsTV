package com.reels.tv

object InstagramAuthService {
    fun buildAuthUrl(clientId: String, redirectUri: String, scope: List<String>): String {
        return "https://api.instagram.com/oauth/authorize" +
                "?client_id=$clientId" +
                "&redirect_uri=$redirectUri" +
                "&scope=${scope.joinToString(",")}" +
                "&response_type=code"
    }
}