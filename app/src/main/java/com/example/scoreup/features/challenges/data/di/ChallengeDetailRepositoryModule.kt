package com.example.scoreup.features.challenges.data.di

import com.example.scoreup.features.challenges.data.repositories.ChallengeDetailRepositoryImpl
import com.example.scoreup.features.challenges.domain.repositories.ChallengeDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChallengeDetailRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChallengeDetailRepository(
        impl: ChallengeDetailRepositoryImpl
    ): ChallengeDetailRepository
}
