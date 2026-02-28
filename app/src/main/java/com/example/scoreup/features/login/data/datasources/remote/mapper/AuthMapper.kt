package com.example.scoreup.features.login.data.datasources.remote.mapper

import android.util.Base64
import com.example.scoreup.features.login.data.datasources.remote.models.LoginResponseDTO
import com.example.scoreup.features.login.domain.entities.User
import org.json.JSONObject

fun LoginResponseDTO.toDomain(): User {
    val payload = decodeJwtPayload(token)
    return User(
        id = payload.optInt("user_id", 0),
        name = payload.optString("name", ""),
        email = payload.optString("email", ""),
        token = token
    )
}

private fun decodeJwtPayload(jwt: String): JSONObject {
    val parts = jwt.split(".")
    if (parts.size < 2) throw IllegalArgumentException("Token JWT inválido")
    val payloadBytes = Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
    return JSONObject(String(payloadBytes, Charsets.UTF_8))
}