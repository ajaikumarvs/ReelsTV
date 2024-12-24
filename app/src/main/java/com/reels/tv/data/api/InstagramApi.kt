package com.reels.tv

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface InstagramApi {
    @GET("me/media")
    suspend fun getReels(
        @Header("Authorization") token: String,
        @Query("fields") fields: String = "id,media_type,media_url,caption,timestamp",
        @Query("media_type") mediaType: String = "VIDEO"
    ): InstagramResponse
}

data class InstagramResponse(
    val data: List<InstagramMedia>,
    val paging: Paging?
)

data class InstagramMedia(
    val id: String,
    val media_type: String,
    val media_url: String,
    val caption: String?,
    val timestamp: String
)

data class Paging(
    val cursors: Cursors,
    val next: String?
)

data class Cursors(
    val before: String,
    val after: String
)