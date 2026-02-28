package com.example.scoreup.features.challenges.data.repositories

import com.example.scoreup.features.challenges.data.datasources.remote.api.ChallengeDetailApi
import com.example.scoreup.features.challenges.data.datasources.remote.mapper.toDetailDomain
import com.example.scoreup.features.challenges.data.datasources.remote.mapper.toDomain
import com.example.scoreup.features.challenges.data.datasources.remote.models.UpdateProgressRequestDTO
import com.example.scoreup.features.challenges.domain.entities.AwardedLogro
import com.example.scoreup.features.challenges.domain.entities.UpdateProgressResult
import com.example.scoreup.features.challenges.domain.entities.UserChallenge
import com.example.scoreup.features.challenges.domain.repositories.ChallengeDetailRepository
import com.example.scoreup.features.home.domain.entities.Challenge
import javax.inject.Inject

class ChallengeDetailRepositoryImpl @Inject constructor(
    private val api: ChallengeDetailApi
) : ChallengeDetailRepository {

    override suspend fun getChallengeById(id: Int): Challenge {
        val response = api.getChallengeById(id)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.reto.toDetailDomain()
        } else {
            throw Exception("Error al obtener el reto")
        }
    }

    override suspend fun joinChallenge(retoId: Int): String {
        val response = api.joinChallenge(retoId)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.message
        } else {
            val errorBody = response.errorBody()?.string() ?: "Error al unirse al reto"
            throw Exception(errorBody)
        }
    }

    override suspend fun updateProgress(retoId: Int, progress: Int): UpdateProgressResult {
        val request = UpdateProgressRequestDTO(progress = progress)
        val response = api.updateProgress(retoId, request)
        if (response.isSuccessful && response.body() != null) {
            val body = response.body()!!
            return UpdateProgressResult(
                message = body.message,
                completed = body.completed,
                logrosAwarded = body.logrosAwarded?.map {
                    AwardedLogro(id = it.id, name = it.name)
                } ?: emptyList()
            )
        } else {
            val errorBody = response.errorBody()?.string() ?: "Error al actualizar progreso"
            throw Exception(errorBody)
        }
    }

    override suspend fun getUserChallengeProgress(retoId: Int): UserChallenge? {
        val response = api.getUserChallenges()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.usuarioRetos
                .find { it.retoId == retoId }
                ?.toDomain()
        } else {
            throw Exception("Error al obtener progreso del usuario")
        }
    }
}
