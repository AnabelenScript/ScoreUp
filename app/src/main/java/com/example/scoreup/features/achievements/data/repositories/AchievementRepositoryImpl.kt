package com.example.scoreup.features.achievements.data.repositories

import com.example.scoreup.features.achievements.data.datasources.remote.api.AchievementApi
import com.example.scoreup.features.achievements.data.datasources.remote.mapper.toDomain
import com.example.scoreup.features.achievements.domain.entities.Achievement
import com.example.scoreup.features.achievements.domain.repositories.AchievementRepository
import javax.inject.Inject

class AchievementRepositoryImpl @Inject constructor(
    private val api: AchievementApi
) : AchievementRepository {

    override suspend fun getAchievements(): List<Achievement> {
        // 1. Obtener catálogo de logros
        val catalogResponse = api.getAllAchievements()
        val catalog = if (catalogResponse.isSuccessful && catalogResponse.body() != null) {
            catalogResponse.body()!!.logros
        } else {
            throw Exception("Error al obtener los logros")
        }

        // 2. Obtener logros del usuario
        val userResponse = api.getUserAchievements()
        val userAchievements = if (userResponse.isSuccessful && userResponse.body() != null) {
            userResponse.body()!!.usuarioLogros
        } else {
            emptyList()
        }

        // 3. Mapear: cruzar catálogo con logros obtenidos
        val unlockedMap = userAchievements.associateBy { it.logroId }

        return catalog.map { dto ->
            val userAchievement = unlockedMap[dto.id]
            dto.toDomain(
                isUnlocked = userAchievement != null,
                obtainedAt = userAchievement?.obtainedAt
            )
        }
    }

    override suspend fun evaluateAchievements(): List<Achievement> {
        val response = api.evaluateAchievements()
        if (!response.isSuccessful) {
            throw Exception("Error al evaluar logros")
        }
        // Después de evaluar, recargar la lista completa
        return getAchievements()
    }
}
