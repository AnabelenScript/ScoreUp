package com.example.scoreup.features.ranking.data.di

import com.example.scoreup.core.qualifiers.ApiNetworkModule
import com.example.scoreup.features.ranking.data.datasources.remote.api.RankingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RankingNetworkModule {

    @Provides
    @Singleton
    fun provideRankingApi(@ApiNetworkModule retrofit: Retrofit): RankingApi {
        return retrofit.create(RankingApi::class.java)
    }
}
