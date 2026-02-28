package com.example.scoreup.features.challenges.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class UserChallengeDTO(
    @SerializedName("UserID") val userId: Int,
    @SerializedName("RetoID") val retoId: Int,
    @SerializedName("Progress") val progress: Int,
    @SerializedName("Status") val status: String,
    @SerializedName("JoinedAt") val joinedAt: String
)

data class UserChallengesResponseDTO(
    @SerializedName("usuario_retos") val usuarioRetos: List<UserChallengeDTO>
)
