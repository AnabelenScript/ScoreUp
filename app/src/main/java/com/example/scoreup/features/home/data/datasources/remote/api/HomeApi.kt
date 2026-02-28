package com.example.scoreup.features.home.data.datasources.remote.api

import com.example.scoreup.features.home.data.datasources.remote.models.ChallengesWsResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET("retos")
    suspend fun getChallenges(): Response<ChallengesWsResponseDTO>
}
