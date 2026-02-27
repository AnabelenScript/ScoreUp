package com.example.scoreup.features.login.data.datasources.remote.mapper

import com.example.scoreup.features.login.data.datasources.remote.models.AuthDTO
import com.example.scoreup.features.login.domain.entities.Auth
import com.example.scoreup.features.login.data.datasources.remote.models.UserDTO
import com.example.scoreup.features.login.domain.entities.User

fun UserDTO.toDomain(): User {
    return User(
        idUsuario = idUsuario,
        nombre = nombre,
        email = email,
        puntosTotales = puntosTotales,
        fechaRegistro = fechaRegistro
    )
}