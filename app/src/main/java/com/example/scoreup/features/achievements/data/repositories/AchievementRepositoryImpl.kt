package com.example.scoreup.features.achievements.data.repositories

import com.example.scoreup.features.achievements.data.datasources.remote.api.AchievementApi
import com.example.scoreup.features.achievements.domain.entities.Achievement
import com.example.scoreup.features.achievements.domain.repositories.AchievementRepository
import javax.inject.Inject

class AchievementRepositoryImpl @Inject constructor(
    private val api: AchievementApi
) : AchievementRepository {

    override suspend fun getAchievements(): List<Achievement> {
        // Simulación de API comentada
        // val response = api.getAchievements()

        // 6 Logros diferentes hardcodeados
        return listOf(
            Achievement(1, "Primer paso", "Completa tu primer reto", "General", true, "footprint"),
            Achievement(2, "Matemático", "Completa 10 retos de mate", "Matemáticas", false, "lock"),
            Achievement(3, "Constante", "7 días de racha activa", "Social", false, "lock"),
            Achievement(4, "Explorador", "Descubre 5 materias", "General", false, "lock"),
            Achievement(5, "Escritor", "Completa 3 ensayos", "Español", false, "lock"),
            Achievement(6, "Científico", "Resuelve 5 laboratorios", "Física", false, "lock")
        )
    }
}
