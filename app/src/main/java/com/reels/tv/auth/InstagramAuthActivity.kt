package com.reels.tv.auth

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.viewModels
import android.content.Intent
import android.widget.Toast

class InstagramAuthActivity : FragmentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Initialize Instagram OAuth
        authViewModel.initializeAuth(
            clientId = BuildConfig.INSTAGRAM_CLIENT_ID,
            redirectUri = BuildConfig.INSTAGRAM_REDIRECT_URI,
            scope = listOf("user_profile", "user_media")
        )

        // Observe auth state
        authViewModel.authState.observe(this) { state ->
            when (state) {
                is AuthState.Authenticated -> navigateToMain()
                is AuthState.Error -> showError(state.message)
                else -> {} // Handle other states if needed
            }
        }
    }

    private fun navigateToMain() {
        // Navigate to your main activity
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}