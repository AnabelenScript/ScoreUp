package com.example.scoreup.features.challenges.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class UpdateProgressRequestDTO(
    @SerializedName("progress") val progress: Int
)

data class UpdateProgressResponseDTO(
    @SerializedName("message") val message: String,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("logros_awarded") val logrosAwarded: List<AwardedLogroDTO>?
)

data class AwardedLogroDTO(
    @SerializedName("ID") val id: Int,
    @SerializedName("Name") val name: String
)
