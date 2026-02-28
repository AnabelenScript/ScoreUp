package com.example.scoreup.features.home.domain.usecases

import com.example.scoreup.features.home.domain.entities.Challenge
import com.example.scoreup.features.home.domain.repositories.ChallengeRepository
import javax.inject.Inject

class GetChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(): List<Challenge> {
        return repository.getAllChallenges()
    }
}
