package com.example.scoreup.features.challenges.domain.usecases

import com.example.scoreup.features.challenges.domain.entities.UpdateProgressResult
import com.example.scoreup.features.challenges.domain.repositories.ChallengeDetailRepository
import javax.inject.Inject

class UpdateChallengeProgressUseCase @Inject constructor(
    private val repository: ChallengeDetailRepository
) {
    suspend operator fun invoke(retoId: Int, progress: Int): UpdateProgressResult {
        return repository.updateProgress(retoId, progress)
    }
}
