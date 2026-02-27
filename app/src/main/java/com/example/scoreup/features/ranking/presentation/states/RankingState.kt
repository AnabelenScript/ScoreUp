package com.example.scoreup.features.ranking.presentation.states

import com.example.scoreup.features.ranking.domain.entities.RankingUser

data class RankingState(
    val ranking: List<RankingUser> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
