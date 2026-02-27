package com.example.scoreup.features.login.data.repositories


import com.example.scoreup.features.login.data.datasources.remote.api.AuthApi
import com.example.scoreup.features.login.data.datasources.remote.models.AuthDTO
import com.example.scoreup.features.login.data.datasources.remote.mapper.toDomain
import com.example.scoreup.features.login.domain.entities.Auth
import com.example.scoreup.features.login.domain.entities.User
import com.example.scoreup.features.login.domain.repositories.UserRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : UserRepository {

    override suspend fun login(auth: Auth): User {
        // Comentado temporalmente por error de CLEARTEXT / API no lista
        /*
        val response = authApi.login(
            AuthDTO(
                email = auth.email,
                password = auth.password
            )
        )

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.toDomain()
        } else {
            throw Exception("Error en login")
        }
        */

        // Usuario de prueba para saltar el login
        return User(
            idUsuario = 1,
            nombre = "Usuario de Prueba",
            email = auth.email,
            puntosTotales = 100,
            fechaRegistro = System.currentTimeMillis()
        )
    }

    override suspend fun register(auth: Auth): User {
        // Simulación de registro exitoso
        return User(
            idUsuario = 1,
            nombre = "Usuario de Prueba",
            email = auth.email,
            puntosTotales = 0,
            fechaRegistro = System.currentTimeMillis()
        )
    }
}
