package com.example.scoreup.features.achievements.domain.entities

data class Achievement(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val isUnlocked: Boolean,
    val iconType: String // e.g., "footprint", "rocket", "book"
)
