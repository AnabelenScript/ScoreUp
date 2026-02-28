package com.example.scoreup.features.challenges.domain.entities

data class UserChallenge(
    val userId: Int,
    val retoId: Int,
    val progress: Int,
    val status: String,
    val joinedAt: String
)
