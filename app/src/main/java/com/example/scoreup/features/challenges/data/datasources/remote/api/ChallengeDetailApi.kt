package com.example.scoreup.features.challenges.data.datasources.remote.api

import com.example.scoreup.features.challenges.data.datasources.remote.models.ChallengeDetailResponseDTO
import com.example.scoreup.features.challenges.data.datasources.remote.models.JoinChallengeResponseDTO
import com.example.scoreup.features.challenges.data.datasources.remote.models.UpdateProgressRequestDTO
import com.example.scoreup.features.challenges.data.datasources.remote.models.UpdateProgressResponseDTO
import com.example.scoreup.features.challenges.data.datasources.remote.models.UserChallengesResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ChallengeDetailApi {

    @GET("retos/{id}")
    suspend fun getChallengeById(@Path("id") id: Int): Response<ChallengeDetailResponseDTO>

    @POST("usuario-retos/{retoId}/join")
    suspend fun joinChallenge(@Path("retoId") retoId: Int): Response<JoinChallengeResponseDTO>

    @PUT("usuario-retos/{retoId}/progress")
    suspend fun updateProgress(
        @Path("retoId") retoId: Int,
        @Body request: UpdateProgressRequestDTO
    ): Response<UpdateProgressResponseDTO>

    @GET("usuario-retos")
    suspend fun getUserChallenges(): Response<UserChallengesResponseDTO>
}
