package com.example.scoreup.features.ranking.presentation.di

import com.example.scoreup.core.navigation.FeatureNavGraph
import com.example.scoreup.core.navigation.RankingNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object RankingModule {

    @Provides
    @IntoSet
    fun provideRankingNavGraph(): FeatureNavGraph {
        return RankingNavGraph
    }
}
