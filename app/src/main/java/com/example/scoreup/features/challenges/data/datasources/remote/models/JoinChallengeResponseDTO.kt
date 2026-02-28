package com.example.scoreup.features.challenges.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class JoinChallengeResponseDTO(
    @SerializedName("message") val message: String
)
