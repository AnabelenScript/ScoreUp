package com.example.scoreup.features.achievements.data.datasources.remote.mapper

import com.example.scoreup.features.achievements.data.datasources.remote.models.AchievementDTO
import com.example.scoreup.features.achievements.domain.entities.Achievement

fun AchievementDTO.toDomain(isUnlocked: Boolean, obtainedAt: String?): Achievement {
    return Achievement(
        id = id,
        name = name,
        description = description,
        requiredPoints = requiredPoints,
        requiredRetos = requiredRetos,
        isUnlocked = isUnlocked,
        obtainedAt = obtainedAt
    )
}
