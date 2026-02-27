package com.example.scoreup.features.ranking.data.di

import com.example.scoreup.features.ranking.data.repositories.RankingRepositoryImpl
import com.example.scoreup.features.ranking.domain.repositories.RankingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RankingRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRankingRepository(
        rankingRepositoryImpl: RankingRepositoryImpl
    ): RankingRepository
}
