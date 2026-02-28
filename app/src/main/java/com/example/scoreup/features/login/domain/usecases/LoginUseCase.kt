package com.example.scoreup.features.login.domain.usecases

import com.example.scoreup.features.login.domain.entities.Auth
import com.example.scoreup.features.login.domain.entities.RegisterData
import com.example.scoreup.features.login.domain.entities.User
import com.example.scoreup.features.login.domain.repositories.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(auth: Auth): Result<User> {
        return try {
            val user = repo.login(auth)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class RegisterUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(registerData: RegisterData): Result<User> {
        return try {
            val user = repo.register(registerData)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
