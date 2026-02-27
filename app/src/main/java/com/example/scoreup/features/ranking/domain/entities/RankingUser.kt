package com.example.scoreup.features.ranking.domain.entities

data class RankingUser(
    val idUsuario: Int,
    val nombre: String,
    val puntos: Int,
    val posicion: Int,
    val tendencia: Int, // 1 sube, -1 baja, 0 igual
    val esUsuarioActual: Boolean = false
)
