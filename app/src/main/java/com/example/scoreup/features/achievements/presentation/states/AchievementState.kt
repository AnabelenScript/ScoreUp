package com.example.scoreup.features.achievements.presentation.states

import com.example.scoreup.features.achievements.domain.entities.Achievement

data class AchievementState(
    val achievements: List<Achievement> = emptyList(),
    val totalUnlocked: Int = 0,
    val totalAchievements: Int = 0,
    val totalPoints: String = "0",
    val completedChallenges: String = "0",
    val seniority: String = "1 día",
    val isLoading: Boolean = false,
    val error: String? = null
)
