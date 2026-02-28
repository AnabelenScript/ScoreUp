package com.example.scoreup.features.login.domain.entities

data class Auth(
    val email: String,
    val password: String
)

data class RegisterData(
    val nombre: String,
    val email: String,
    val password: String,
    val phone: String
)
