package com.example.scoreup.features.login.data.datasources.remote.models

data class AuthDTO(
    val email: String,
    val password: String
)

data class UserDTO(
    val idUsuario: Int,
    val nombre: String,
    val email: String,
    val puntosTotales: Int,
    val fechaRegistro: Long
)