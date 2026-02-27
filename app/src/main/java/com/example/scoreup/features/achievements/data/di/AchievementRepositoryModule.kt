package com.example.scoreup.features.achievements.data.di

import com.example.scoreup.features.achievements.data.repositories.AchievementRepositoryImpl
import com.example.scoreup.features.achievements.domain.repositories.AchievementRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AchievementRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAchievementRepository(
        achievementRepositoryImpl: AchievementRepositoryImpl
    ): AchievementRepository
}
