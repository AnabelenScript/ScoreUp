package com.example.scoreup.features.login.data.repositories


import com.example.scoreup.core.storage.TokenManager
import com.example.scoreup.features.login.data.datasources.remote.api.AuthApi
import com.example.scoreup.features.login.data.datasources.remote.models.LoginRequestDTO
import com.example.scoreup.features.login.data.datasources.remote.models.RegisterRequestDTO
import com.example.scoreup.features.login.data.datasources.remote.mapper.toDomain
import com.example.scoreup.features.login.domain.entities.Auth
import com.example.scoreup.features.login.domain.entities.RegisterData
import com.example.scoreup.features.login.domain.entities.User
import com.example.scoreup.features.login.domain.repositories.UserRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) : UserRepository {

    override suspend fun login(auth: Auth): User {
        val response = authApi.login(
            LoginRequestDTO(
                email = auth.email,
                password = auth.password
            )
        )

        if (response.isSuccessful && response.body() != null) {
            val user = response.body()!!.toDomain()
            tokenManager.saveToken(user.token)
            return user
        } else {
            val errorBody = response.errorBody()?.string() ?: "Error en login"
            throw Exception(errorBody)
        }
    }

    override suspend fun register(registerData: RegisterData): User {
        val registerResponse = authApi.register(
            RegisterRequestDTO(
                nombre = registerData.nombre,
                email = registerData.email,
                password = registerData.password,
                phone = registerData.phone
            )
        )

        if (!registerResponse.isSuccessful || registerResponse.body() == null) {
            val errorBody = registerResponse.errorBody()?.string() ?: "Error en registro"
            throw Exception(errorBody)
        }

        // Auto-login después de registro exitoso para obtener token
        return login(
            Auth(email = registerData.email, password = registerData.password)
        )
    }
}
