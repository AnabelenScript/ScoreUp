package com.example.scoreup.features.ranking.data.datasources.remote.api

import com.example.scoreup.features.ranking.data.datasources.remote.models.RankingUserDTO
import retrofit2.Response
import retrofit2.http.GET

interface RankingApi {
    @GET("ranking")
    suspend fun getRanking(): Response<List<RankingUserDTO>>
}
