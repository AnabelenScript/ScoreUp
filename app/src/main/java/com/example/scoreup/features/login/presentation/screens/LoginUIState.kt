package com.example.scoreup.features.login.presentation.screens


import com.example.scoreup.features.login.domain.entities.User

data class UsersUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val phone: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null
)
