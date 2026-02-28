package com.example.scoreup.features.challenges.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class ChallengeDetailResponseDTO(
    @SerializedName("reto") val reto: ChallengeDTO
)
