package com.example.scoreup.features.ranking.data.datasources.remote.api

import com.example.scoreup.features.login.data.datasources.remote.models.UserDTO
import retrofit2.Response
import retrofit2.http.GET

interface RankingApi {
    @GET("ranking")
    suspend fun getRanking(): Response<List<UserDTO>>
}
