package com.example.scoreup.features.challenges.domain.entities

data class UpdateProgressResult(
    val message: String,
    val completed: Boolean,
    val logrosAwarded: List<AwardedLogro>
)

data class AwardedLogro(
    val id: Int,
    val name: String
)
