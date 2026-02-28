package com.example.scoreup.features.challenges.domain.repositories

import com.example.scoreup.features.challenges.domain.entities.UpdateProgressResult
import com.example.scoreup.features.challenges.domain.entities.UserChallenge
import com.example.scoreup.features.home.domain.entities.Challenge

interface ChallengeDetailRepository {
    suspend fun getChallengeById(id: Int): Challenge
    suspend fun joinChallenge(retoId: Int): String
    suspend fun updateProgress(retoId: Int, progress: Int): UpdateProgressResult
    suspend fun getUserChallengeProgress(retoId: Int): UserChallenge?
}
