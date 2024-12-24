package com.reels.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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