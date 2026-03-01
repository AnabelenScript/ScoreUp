package com.example.scoreup.features.challenges.data.repositories

import android.util.Log
import com.example.scoreup.features.challenges.data.datasources.remote.api.ChallengeApi
import com.example.scoreup.features.challenges.data.datasources.remote.mapper.toData
import com.example.scoreup.features.challenges.domain.entities.Challenge
import com.example.scoreup.features.challenges.domain.repositories.ChallengeRepository
import com.example.scoreup.features.home.data.datasources.remote.ChallengeWebSocketDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeApi: ChallengeApi,
    private val webSocketDataSource: ChallengeWebSocketDataSource
) : ChallengeRepository {

    override suspend fun getAllChallenges(): List<Challenge> {
        val response = challengeApi.getChallenges()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.retos.map { dto ->
                Challenge(
                    idReto = dto.id,
                    idUsuario = dto.userId,
                    materia = dto.subject ?: "",
                    descripcion = dto.description ?: "",
                    meta = dto.goal,
                    puntosOtorgados = dto.pointsAwarded,
                    fechaLimite = dto.deadline ?: "",
                    fechaCreacion = dto.createdAt ?: ""
                )
            }
        } else {
            throw Exception("Error al obtener los retos")
        }
    }

    override fun observeChallenges(): Flow<List<Challenge>> {
        return webSocketDataSource.challengesFlow.map { dtos ->
            dtos.map { dto ->
                Challenge(
                    idReto = dto.id,
                    idUsuario = dto.userId,
                    materia = dto.subject ?: "",
                    descripcion = dto.description ?: "",
                    meta = dto.goal,
                    puntosOtorgados = dto.pointsAwarded,
                    fechaLimite = dto.deadline ?: "",
                    fechaCreacion = dto.createdAt ?: ""
                )
            }
        }
    }

    override fun connectWebSocket() {
        webSocketDataSource.connect()
    }

    override fun disconnectWebSocket() {
        webSocketDataSource.disconnect()
    }

    override suspend fun createChallenge(challenge: Challenge): Challenge {
        Log.d("ChallengeRepo", "Enviando reto a API: $challenge")
        val response = challengeApi.createChallenge(challenge.toData())
        
        if (response.isSuccessful && response.body() != null) {
            val dto = response.body()!!
            Log.d("ChallengeRepo", "API respondió con DTO: $dto")
            
            // Verificamos nulos antes de crear la entidad de dominio
            val result = Challenge(
                idReto = dto.id,
                idUsuario = dto.userId,
                materia = dto.subject ?: "Sin Materia",
                descripcion = dto.description ?: "Sin Descripción",
                meta = dto.goal,
                puntosOtorgados = dto.pointsAwarded,
                fechaLimite = dto.deadline ?: "",
                fechaCreacion = dto.createdAt ?: ""
            )
            Log.d("ChallengeRepo", "Entidad de dominio creada con éxito: $result")
            return result
        } else {
            val errorMsg = response.errorBody()?.string()
            Log.e("ChallengeRepo", "Error en API: $errorMsg")
            throw Exception("Error al crear el reto: $errorMsg")
        }
    }
}
