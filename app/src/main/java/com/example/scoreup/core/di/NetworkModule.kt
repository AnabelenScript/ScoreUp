package com.example.scoreup.core.di

import com.example.scoreup.core.network.interceptor.AuthInterceptor
import com.example.scoreup.core.qualifiers.ApiNetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @ApiNetworkModule
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://184.72.233.162:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}