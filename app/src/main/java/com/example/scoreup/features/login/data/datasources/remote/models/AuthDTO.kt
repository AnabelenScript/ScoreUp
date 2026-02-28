package com.example.scoreup.features.login.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

// --- Login ---
data class LoginRequestDTO(
    val email: String,
    val password: String
)

data class LoginResponseDTO(
    val message: String,
    val token: String
)

data class RegisterRequestDTO(
    val nombre: String,
    val email: String,
    val password: String,
    val phone: String
)

data class RegisterResponseDTO(
    val email: String,
    val id: Int,
    val message: String
)