package com.example.scoreup.features.home.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

/**
 * DTO que envuelve la lista de retos recibida desde el WebSocket.
 * El canal "retos" envía: {"retos": [<lista completa de retos>]}
 */
data class ChallengesWsResponseDTO(
    @SerializedName("retos") val retos: List<ChallengeDTO>
)
