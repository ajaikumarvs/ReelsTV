package com.reels.tv

data class Reel(
    val id: String,
    val videoUrl: String,
    val caption: String = "",
    val author: String = ""
)