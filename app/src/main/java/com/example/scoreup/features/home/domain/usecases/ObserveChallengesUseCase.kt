package com.example.scoreup.features.home.domain.usecases

import com.example.scoreup.features.home.domain.entities.Challenge
import com.example.scoreup.features.home.domain.repositories.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case que expone un Flow reactivo de retos actualizados
 * en tiempo real a través del WebSocket.
 */
class ObserveChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    operator fun invoke(): Flow<List<Challenge>> {
        return repository.observeChallenges()
    }
}
