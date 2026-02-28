package com.example.scoreup.features.challenges.presentation.states

import com.example.scoreup.features.home.domain.entities.Challenge

data class ChallengesListState(
    val challenges: List<Challenge> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
