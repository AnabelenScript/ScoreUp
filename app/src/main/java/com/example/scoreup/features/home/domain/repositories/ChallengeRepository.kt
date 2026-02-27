package com.example.scoreup.features.home.domain.repositories

import com.example.scoreup.features.home.domain.entities.Challenge

interface ChallengeRepository {
    suspend fun getAllChallenges(): List<Challenge>
}
