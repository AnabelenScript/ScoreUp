package com.example.scoreup.features.login.data.datasources.remote.api

import com.example.scoreup.features.login.data.datasources.remote.models.AuthDTO
import com.example.scoreup.features.login.data.datasources.remote.models.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {
    @POST("login")
    suspend fun login(
        @Body authDTO: AuthDTO
    ): Response<UserDTO>

    @POST("register")
    suspend fun register(
        @Body authDTO: AuthDTO
    ): Response<UserDTO>
}