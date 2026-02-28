package com.example.scoreup.features.challenges.data.di

import com.example.scoreup.features.home.data.repositories.ChallengeRepositoryImpl
import com.example.scoreup.features.home.domain.repositories.ChallengeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChallengeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChallengeRepository(
        challengeRepositoryImpl: ChallengeRepositoryImpl
    ): ChallengeRepository
}
