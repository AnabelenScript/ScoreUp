package com.example.scoreup.features.challenges.presentation.states

import com.example.scoreup.features.challenges.domain.entities.AwardedLogro
import com.example.scoreup.features.challenges.domain.entities.UserChallenge
import com.example.scoreup.features.home.domain.entities.Challenge

data class ChallengeDetailState(
    val challenge: Challenge? = null,
    val userChallenge: UserChallenge? = null,
    val isLoading: Boolean = false,
    val isJoined: Boolean = false,
    val isJoining: Boolean = false,
    val isUpdatingProgress: Boolean = false,
    val progressIncrement: Int = 1,
    val error: String? = null,
    val successMessage: String? = null,
    val completed: Boolean = false,
    val logrosAwarded: List<AwardedLogro> = emptyList()
)
