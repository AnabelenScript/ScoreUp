package com.example.scoreup.features.achievements.data.datasources.remote.api

import com.example.scoreup.features.achievements.data.datasources.remote.models.AchievementsResponseDTO
import com.example.scoreup.features.achievements.data.datasources.remote.models.EvaluateAchievementsResponseDTO
import com.example.scoreup.features.achievements.data.datasources.remote.models.UserAchievementsResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface AchievementApi {

    /** Catálogo completo de logros */
    @GET("logros")
    suspend fun getAllAchievements(): Response<AchievementsResponseDTO>

    /** Logros obtenidos por el usuario autenticado */
    @GET("usuario-logros")
    suspend fun getUserAchievements(): Response<UserAchievementsResponseDTO>

    /** Evalúa y asigna logros desbloqueados */
    @POST("usuario-logros/evaluate")
    suspend fun evaluateAchievements(): Response<EvaluateAchievementsResponseDTO>
}
