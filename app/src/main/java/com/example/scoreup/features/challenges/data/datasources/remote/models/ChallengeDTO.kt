package com.example.scoreup.features.challenges.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class ChallengeDTO(
    @SerializedName("ID") val id: Int,
    @SerializedName("UserID") val userId: Int,
    @SerializedName("Subject") val subject: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Goal") val goal: Int,
    @SerializedName("PointsAwarded") val pointsAwarded: Int,
    @SerializedName("Deadline") val deadline: String,
    @SerializedName("CreatedAt") val createdAt: String
)
