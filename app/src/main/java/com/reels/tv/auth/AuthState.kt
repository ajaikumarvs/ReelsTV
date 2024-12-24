package com.reels.tv

sealed class AuthState {
    data class Authenticated(val token: String) : AuthState()
    data class RequiresAuth(val authUrl: String) : AuthState()
    data class Error(val message: String) : AuthState()
}