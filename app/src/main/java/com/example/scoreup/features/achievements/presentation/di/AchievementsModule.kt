package com.example.scoreup.features.achievements.presentation.di

import com.example.scoreup.core.navigation.FeatureNavGraph
import com.example.scoreup.core.navigation.AchievementsNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object AchievementsModule {

    @Provides
    @IntoSet
    fun provideAchievementsNavGraph(): FeatureNavGraph {
        return AchievementsNavGraph
    }
}
