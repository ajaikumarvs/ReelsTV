package com.reels.tv

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.activity.viewModels
import android.content.Intent
import android.content.Context

class InstagramAuthActivity : FragmentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.reels.tv.R.layout.activity_auth)

        // Initialize Instagram OAuth using values from secrets.xml
        authViewModel.initializeAuth(
            clientId = getString(com.reels.tv.R.string.instagram_client_id),
            redirectUri = getString(com.reels.tv.R.string.instagram_redirect_uri),
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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}