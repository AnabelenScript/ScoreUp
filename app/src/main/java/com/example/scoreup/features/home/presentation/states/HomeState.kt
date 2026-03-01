package com.example.scoreup.features.home.presentation.states

import com.example.scoreup.features.home.domain.entities.Challenge

data class HomeState(
    val userName: String = "",
    val points: Int = 0,
    val ranking: Int = 0,
    val seniority: String = "",
    val challenges: List<Challenge> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
