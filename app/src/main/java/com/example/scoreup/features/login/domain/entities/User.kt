package com.example.scoreup.features.login.domain.entities

data class User(
    val idUsuario: Int,
    val nombre: String,
    val email: String,
    val puntosTotales: Int,
    val fechaRegistro: Long
)
