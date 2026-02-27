package com.example.scoreup.features.login.data.di

import com.example.scoreup.core.navigation.FeatureNavGraph
import com.example.scoreup.core.navigation.LoginNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object UsersModule {

    @Provides
    @IntoSet
    fun provideLoginNavGraph(): FeatureNavGraph {
        return LoginNavGraph
    }
}