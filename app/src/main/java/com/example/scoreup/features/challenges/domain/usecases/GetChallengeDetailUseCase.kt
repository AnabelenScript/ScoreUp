package com.example.scoreup.features.challenges.domain.usecases

import com.example.scoreup.features.challenges.domain.repositories.ChallengeDetailRepository
import com.example.scoreup.features.home.domain.entities.Challenge
import javax.inject.Inject

class GetChallengeDetailUseCase @Inject constructor(
    private val repository: ChallengeDetailRepository
) {
    suspend operator fun invoke(id: Int): Challenge {
        return repository.getChallengeById(id)
    }
}
