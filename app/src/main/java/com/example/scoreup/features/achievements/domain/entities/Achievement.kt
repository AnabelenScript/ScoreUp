package com.example.scoreup.features.achievements.domain.entities

data class Achievement(
    val id: Int,
    val name: String,
    val description: String,
    val requiredPoints: Int,
    val requiredRetos: Int,
    val isUnlocked: Boolean,
    val obtainedAt: String? = null
)
