package com.example.scoreup.features.ranking.data.datasources.remote.api

import com.example.scoreup.features.ranking.data.datasources.remote.models.RankingResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface RankingApi {
    @GET("users/rank")
    suspend fun getRanking(): Response<RankingResponseDTO>
}
