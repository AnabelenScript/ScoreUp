package com.example.scoreup.features.challenges.domain.usecases

import com.example.scoreup.features.challenges.domain.entities.Challenge
import com.example.scoreup.features.challenges.domain.repositories.ChallengeRepository
import javax.inject.Inject

class CreateChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challenge: Challenge): Challenge {
        return repository.createChallenge(challenge)
    }
}
