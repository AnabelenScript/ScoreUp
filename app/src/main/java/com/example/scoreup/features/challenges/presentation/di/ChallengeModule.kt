package com.example.scoreup.features.challenges.presentation.di

import com.example.scoreup.core.navigation.FeatureNavGraph
import com.example.scoreup.core.navigation.HomeNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object ChallengeModule {

    @Provides
    @IntoSet
    fun provideHomeNavGraph(): FeatureNavGraph {
        return HomeNavGraph
    }
}
