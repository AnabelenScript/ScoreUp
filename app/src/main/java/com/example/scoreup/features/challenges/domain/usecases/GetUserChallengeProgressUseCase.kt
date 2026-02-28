package com.example.scoreup.features.challenges.domain.usecases

import com.example.scoreup.features.challenges.domain.entities.UserChallenge
import com.example.scoreup.features.challenges.domain.repositories.ChallengeDetailRepository
import javax.inject.Inject

class GetUserChallengeProgressUseCase @Inject constructor(
    private val repository: ChallengeDetailRepository
) {
    suspend operator fun invoke(retoId: Int): UserChallenge? {
        return repository.getUserChallengeProgress(retoId)
    }
}
