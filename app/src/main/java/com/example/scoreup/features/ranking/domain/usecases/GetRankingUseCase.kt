package com.example.scoreup.features.ranking.domain.usecases

import com.example.scoreup.features.ranking.domain.entities.RankingUser
import com.example.scoreup.features.ranking.domain.repositories.RankingRepository
import javax.inject.Inject

class GetRankingUseCase @Inject constructor(
    private val repository: RankingRepository
) {
    suspend operator fun invoke(): List<RankingUser> {
        return repository.getRanking()
    }
}
