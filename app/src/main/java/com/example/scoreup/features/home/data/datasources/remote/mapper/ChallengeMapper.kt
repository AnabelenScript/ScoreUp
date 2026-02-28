package com.example.scoreup.features.home.data.datasources.remote.mapper

import com.example.scoreup.features.home.data.datasources.remote.models.ChallengeDTO
import com.example.scoreup.features.home.domain.entities.Challenge

fun ChallengeDTO.toDomain(): Challenge {
    return Challenge(
        idReto = id,
        idUsuario = userId,
        materia = subject,
        descripcion = description,
        meta = goal,
        puntosOtorgados = pointsAwarded,
        fechaLimite = deadline,
        fechaCreacion = createdAt
    )
}
