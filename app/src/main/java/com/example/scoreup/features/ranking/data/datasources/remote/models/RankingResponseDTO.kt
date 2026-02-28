package com.example.scoreup.features.ranking.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

/**
 * DTO que envuelve la lista del ranking recibida desde REST y WebSocket.
 * Formato: {"ranking": [{...}, ...]}
 */
data class RankingResponseDTO(
    @SerializedName("ranking") val ranking: List<RankingUserDTO>
)
