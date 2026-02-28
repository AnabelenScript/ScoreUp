package com.example.scoreup.features.ranking.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class RankingUserDTO(
    @SerializedName("ID") val id: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("TotalScore") val totalScore: Int
)
