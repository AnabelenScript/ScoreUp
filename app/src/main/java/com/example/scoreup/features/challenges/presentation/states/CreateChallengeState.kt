package com.example.scoreup.features.challenges.presentation.states

data class CreateChallengeState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val subject: String = "",
    val description: String = "",
    val goal: String = "",
    val points: String = "",
    val deadline: String = ""
)
