package com.example.scoreup.features.ranking.domain.repositories

import com.example.scoreup.features.ranking.domain.entities.RankingUser

interface RankingRepository {
    suspend fun getRanking(): List<RankingUser>
}
