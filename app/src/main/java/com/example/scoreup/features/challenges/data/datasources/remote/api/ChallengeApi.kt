package com.example.scoreup.features.challenges.data.datasources.remote.api

import com.example.scoreup.features.challenges.data.datasources.remote.models.ChallengesWsResponseDTO
import com.example.scoreup.features.challenges.data.datasources.remote.models.ChallengeDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChallengeApi {
    @GET("retos")
    suspend fun getChallenges(): Response<ChallengesWsResponseDTO>

    @POST("retos")
    suspend fun createChallenge(@Body challenge: ChallengeDTO): Response<ChallengeDTO>
}
