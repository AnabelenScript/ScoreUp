package com.example.scoreup.features.ranking.data.datasources.remote.models

data class RankingUserDTO(
    val idUsuario: Int,
    val nombre: String,
    val puntos: Int,
    val posicion: Int,
    val tendencia: Int
)
