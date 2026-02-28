package com.example.scoreup.features.challenges.data.di

import com.example.scoreup.core.qualifiers.ApiNetworkModule
import com.example.scoreup.features.challenges.data.datasources.remote.api.ChallengeDetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChallengeDetailNetworkModule {

    @Provides
    @Singleton
    fun provideChallengeDetailApi(
        @ApiNetworkModule retrofit: Retrofit
    ): ChallengeDetailApi {
        return retrofit.create(ChallengeDetailApi::class.java)
    }
}
