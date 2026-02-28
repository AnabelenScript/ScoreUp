package com.example.scoreup.features.ranking.domain.repositories

import com.example.scoreup.features.ranking.domain.entities.RankingUser
import kotlinx.coroutines.flow.Flow

interface RankingRepository {
    suspend fun getRanking(): List<RankingUser>

    fun observeRanking(): Flow<List<RankingUser>>

    fun connectWebSocket()

    fun disconnectWebSocket()
}
