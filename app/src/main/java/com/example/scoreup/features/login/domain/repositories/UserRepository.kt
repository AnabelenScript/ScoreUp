package com.example.scoreup.features.login.domain.repositories

import com.example.scoreup.features.login.data.datasources.remote.models.AuthDTO
import com.example.scoreup.features.login.data.datasources.remote.models.GetResponse
import com.example.scoreup.features.login.domain.entities.Auth
import com.example.scoreup.features.login.domain.entities.User


interface UserRepository {

    suspend fun login(auth: Auth): User

    suspend fun register(auth: Auth): User
}