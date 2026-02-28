package com.example.scoreup.features.challenges.data.datasources.remote.api

import com.example.scoreup.features.home.data.datasources.remote.models.ChallengesWsResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface ChallengeApi {
    @GET("retos")
    suspend fun getChallenges(): Response<ChallengesWsResponseDTO>
}
