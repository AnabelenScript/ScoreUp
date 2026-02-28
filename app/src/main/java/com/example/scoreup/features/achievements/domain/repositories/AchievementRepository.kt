package com.example.scoreup.features.achievements.domain.repositories

import com.example.scoreup.features.achievements.domain.entities.Achievement

interface AchievementRepository {
    suspend fun getAchievements(): List<Achievement>
    suspend fun evaluateAchievements(): List<Achievement>
}
