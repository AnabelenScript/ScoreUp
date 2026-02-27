package com.example.scoreup.features.achievements.data.datasources.remote.api

import com.example.scoreup.features.achievements.data.datasources.remote.models.AchievementDTO
import retrofit2.Response
import retrofit2.http.GET

interface AchievementApi {
    @GET("achievements")
    suspend fun getAchievements(): Response<List<AchievementDTO>>
}
