package com.example.scoreup.features.home.data.datasources.remote.api

import com.example.scoreup.features.home.data.datasources.remote.models.ChallengeDTO
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET("challenges")
    suspend fun getChallenges(): Response<List<ChallengeDTO>>
}
