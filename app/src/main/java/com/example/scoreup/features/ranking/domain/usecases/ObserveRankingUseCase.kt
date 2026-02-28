package com.example.scoreup.features.ranking.domain.usecases

import com.example.scoreup.features.ranking.domain.entities.RankingUser
import com.example.scoreup.features.ranking.domain.repositories.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRankingUseCase @Inject constructor(
    private val repository: RankingRepository
) {
    operator fun invoke(): Flow<List<RankingUser>> {
        return repository.observeRanking()
    }
}
