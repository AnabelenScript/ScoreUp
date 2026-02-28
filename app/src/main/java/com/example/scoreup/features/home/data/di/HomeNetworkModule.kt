package com.example.scoreup.features.home.data.di

import com.example.scoreup.core.qualifiers.ApiNetworkModule
import com.example.scoreup.features.home.data.datasources.remote.api.HomeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeNetworkModule {

    @Provides
    @Singleton
    fun provideHomeApi(
        @ApiNetworkModule retrofit: Retrofit
    ): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }
}
