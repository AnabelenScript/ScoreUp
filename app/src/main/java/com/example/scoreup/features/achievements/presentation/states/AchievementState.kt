package com.example.scoreup.features.achievements.presentation.states

import com.example.scoreup.features.achievements.domain.entities.Achievement

data class AchievementState(
    val achievements: List<Achievement> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val totalUnlocked: Int get() = achievements.count { it.isUnlocked }
    val totalAchievements: Int get() = achievements.size
}
