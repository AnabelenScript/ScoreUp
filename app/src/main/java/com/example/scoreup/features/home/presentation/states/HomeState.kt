package com.example.scoreup.features.home.presentation.states

import com.example.scoreup.features.home.domain.entities.Challenge

data class HomeState(
    val challenges: List<Challenge> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
