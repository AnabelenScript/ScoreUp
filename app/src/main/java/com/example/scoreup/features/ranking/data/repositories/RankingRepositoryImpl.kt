package com.example.scoreup.features.ranking.data.repositories

import com.example.scoreup.features.ranking.data.datasources.remote.api.RankingApi
import com.example.scoreup.features.ranking.domain.entities.RankingUser
import com.example.scoreup.features.ranking.domain.repositories.RankingRepository
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val api: RankingApi
) : RankingRepository {

    override suspend fun getRanking(): List<RankingUser> {
        // Simulación de API comentada
        // val response = api.getRanking()
        
        // Datos Hardcoded para la vista
        return listOf(
            RankingUser(1, "Juan P.", 340, 1, 0),
            RankingUser(2, "Luis R.", 295, 2, 0),
            RankingUser(3, "Ana N.", 295, 3, 0),
            RankingUser(4, "Carlos Molina (Tú)", 185, 4, 1, esUsuarioActual = true),
            RankingUser(5, "Diego Fernandez", 170, 5, -1),
            RankingUser(6, "Alberto Lara", 170, 6, -1),
            RankingUser(7, "Roberto Juarez", 167, 7, 1),
            RankingUser(8, "Jonathan Hernandez", 163, 8, 1),
            RankingUser(9, "Angela Ruiz", 148, 9, -1),
            RankingUser(10, "Jose Perez", 124, 10, 1)
        )
    }
}
