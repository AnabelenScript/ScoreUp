package com.example.scoreup.features.challenges.data.repositories

import com.example.scoreup.features.home.data.datasources.remote.ChallengeWebSocketDataSource
import com.example.scoreup.features.home.data.datasources.remote.api.HomeApi
import com.example.scoreup.features.home.data.datasources.remote.mapper.toDomain
import com.example.scoreup.features.home.domain.entities.Challenge
import com.example.scoreup.features.home.domain.repositories.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val webSocketDataSource: ChallengeWebSocketDataSource
) : ChallengeRepository {

    override suspend fun getAllChallenges(): List<Challenge> {
        val response = homeApi.getChallenges()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.retos.map { it.toDomain() }
        } else {
            throw Exception("Error al obtener los retos")
        }
    }

    override fun observeChallenges(): Flow<List<Challenge>> {
        return webSocketDataSource.challengesFlow.map { dtos ->
            dtos.map { it.toDomain() }
        }
    }

    override fun connectWebSocket() {
        webSocketDataSource.connect()
    }

    override fun disconnectWebSocket() {
        webSocketDataSource.disconnect()
    }
}
