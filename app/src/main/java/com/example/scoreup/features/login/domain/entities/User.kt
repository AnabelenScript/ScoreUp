package com.example.scoreup.features.login.domain.entities

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val token: String
)
