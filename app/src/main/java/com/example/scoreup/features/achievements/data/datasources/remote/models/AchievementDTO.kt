package com.example.scoreup.features.achievements.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class AchievementDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("is_unlocked") val isUnlocked: Boolean,
    @SerializedName("icon_type") val iconType: String
)
