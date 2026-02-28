package com.example.scoreup.features.login.domain.repositories

import com.example.scoreup.features.login.domain.entities.Auth
import com.example.scoreup.features.login.domain.entities.RegisterData
import com.example.scoreup.features.login.domain.entities.User


interface UserRepository {

    suspend fun login(auth: Auth): User

    suspend fun register(registerData: RegisterData): User
}