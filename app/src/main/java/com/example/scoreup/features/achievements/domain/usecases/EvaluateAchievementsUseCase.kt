package com.example.scoreup.features.achievements.domain.usecases

import com.example.scoreup.features.achievements.domain.entities.Achievement
import com.example.scoreup.features.achievements.domain.repositories.AchievementRepository
import javax.inject.Inject

class EvaluateAchievementsUseCase @Inject constructor(
    private val repository: AchievementRepository
) {
    suspend operator fun invoke(): List<Achievement> {
        return repository.evaluateAchievements()
    }
}
