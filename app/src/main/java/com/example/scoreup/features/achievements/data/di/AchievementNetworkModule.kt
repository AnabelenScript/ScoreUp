package com.example.scoreup.features.achievements.data.di

import com.example.scoreup.core.qualifiers.ApiNetworkModule
import com.example.scoreup.features.achievements.data.datasources.remote.api.AchievementApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AchievementNetworkModule {

    @Provides
    @Singleton
    fun provideAchievementApi(@ApiNetworkModule retrofit: Retrofit): AchievementApi {
        return retrofit.create(AchievementApi::class.java)
    }
}
