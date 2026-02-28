package com.example.scoreup.features.login.data.datasources.remote.api

import com.example.scoreup.features.login.data.datasources.remote.models.LoginRequestDTO
import com.example.scoreup.features.login.data.datasources.remote.models.LoginResponseDTO
import com.example.scoreup.features.login.data.datasources.remote.models.RegisterRequestDTO
import com.example.scoreup.features.login.data.datasources.remote.models.RegisterResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {
    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequestDTO
    ): Response<LoginResponseDTO>

    @POST("users/register")
    suspend fun register(
        @Body request: RegisterRequestDTO
    ): Response<RegisterResponseDTO>
}