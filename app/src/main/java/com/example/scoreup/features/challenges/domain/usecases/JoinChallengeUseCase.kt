package com.example.scoreup.features.challenges.domain.usecases

import com.example.scoreup.features.challenges.domain.repositories.ChallengeDetailRepository
import javax.inject.Inject

class JoinChallengeUseCase @Inject constructor(
    private val repository: ChallengeDetailRepository
) {
    suspend operator fun invoke(retoId: Int): String {
        return repository.joinChallenge(retoId)
    }
}
