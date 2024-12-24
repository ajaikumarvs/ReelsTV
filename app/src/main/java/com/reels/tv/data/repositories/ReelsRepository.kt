package com.reels.tv

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReelsRepository {
    private val api: InstagramApi

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://graph.instagram.com/v12.0/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(InstagramApi::class.java)
    }

    suspend fun getReels(hashtag: String? = null): List<Reel> {
        try {
            // Get the access token from your storage
            val token = getStoredAccessToken()

            val response = api.getReels(
                token = "Bearer $token",
                fields = "id,media_type,media_url,caption,timestamp"
            )

            return response.data
                .filter { it.media_type == "VIDEO" }
                .map { media ->
                    Reel(
                        id = media.id,
                        videoUrl = media.media_url,
                        caption = media.caption ?: "",
                        author = "" // Instagram Basic Display API doesn't provide author info
                    )
                }
        } catch (e: Exception) {
            throw ReelsException("Failed to fetch reels: ${e.message}")
        }
    }

    private fun getStoredAccessToken(): String {
        // TODO: Implement access token storage and retrieval
        // You should store this securely using EncryptedSharedPreferences
        throw NotImplementedError("Implement access token storage")
    }
}

class ReelsException(message: String) : Exception(message)