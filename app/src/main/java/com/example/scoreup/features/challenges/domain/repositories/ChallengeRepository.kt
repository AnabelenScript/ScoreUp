package com.example.scoreup.features.challenges.domain.repositories

import com.example.scoreup.features.home.domain.entities.Challenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getAllChallenges(): List<Challenge>

    /** Flow que emite la lista actualizada de retos en tiempo real vía WebSocket */
    fun observeChallenges(): Flow<List<Challenge>>

    /** Inicia la conexión WebSocket al canal de retos */
    fun connectWebSocket()

    /** Cierra la conexión WebSocket */
    fun disconnectWebSocket()
}
