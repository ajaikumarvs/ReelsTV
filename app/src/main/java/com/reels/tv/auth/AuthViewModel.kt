package com.reels.tv.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// First, define the AuthState sealed class
sealed class AuthState {
    data class Authenticated(val token: String) : AuthState()
    data class RequiresAuth(val authUrl: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

// Create InstagramAuthService object
object InstagramAuthService {
    fun buildAuthUrl(clientId: String, redirectUri: String, scope: List<String>): String {
        return "https://api.instagram.com/oauth/authorize" +
                "?client_id=$clientId" +
                "&redirect_uri=$redirectUri" +
                "&scope=${scope.joinToString(",")}" +
                "&response_type=code"
    }
}

class AuthViewModel : ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    fun initializeAuth(clientId: String, redirectUri: String, scope: List<String>) {
        viewModelScope.launch {
            try {
                val authUrl = InstagramAuthService.buildAuthUrl(
                    clientId = clientId,
                    redirectUri = redirectUri,
                    scope = scope
                )
                _authState.value = AuthState.RequiresAuth(authUrl)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Auth failed")
            }
        }
    }
}