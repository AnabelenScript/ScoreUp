package com.example.scoreup.features.ranking.data.repositories

import com.example.scoreup.core.storage.TokenManager
import com.example.scoreup.features.ranking.data.datasources.remote.RankingWebSocketDataSource
import com.example.scoreup.features.ranking.data.datasources.remote.api.RankingApi
import com.example.scoreup.features.ranking.data.datasources.remote.mapper.toDomainList
import com.example.scoreup.features.ranking.domain.entities.RankingUser
import com.example.scoreup.features.ranking.domain.repositories.RankingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val api: RankingApi,
    private val webSocketDataSource: RankingWebSocketDataSource,
    private val tokenManager: TokenManager
) : RankingRepository {

    override suspend fun getRanking(): List<RankingUser> {
        val response = api.getRanking()
        if (response.isSuccessful && response.body() != null) {
            val currentUserId = tokenManager.getUserId()
            return response.body()!!.ranking.toDomainList(currentUserId)
        } else {
            throw Exception("Error al obtener el ranking")
        }
    }

    override fun observeRanking(): Flow<List<RankingUser>> {
        val currentUserId = runBlocking { tokenManager.getUserId() }
        return webSocketDataSource.rankingFlow.map { dtos ->
            dtos.toDomainList(currentUserId)
        }
    }

    override fun connectWebSocket() {
        webSocketDataSource.connect()
    }

    override fun disconnectWebSocket() {
        webSocketDataSource.disconnect()
    }
}
