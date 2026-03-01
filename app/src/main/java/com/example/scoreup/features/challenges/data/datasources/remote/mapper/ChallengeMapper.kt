package com.example.scoreup.features.challenges.data.datasources.remote.mapper

import com.example.scoreup.features.challenges.data.datasources.remote.models.ChallengeDTO
import com.example.scoreup.features.challenges.domain.entities.Challenge

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

fun Challenge.toData(): ChallengeDTO {
    return ChallengeDTO(
        id = idReto,
        userId = idUsuario,
        subject = materia,
        description = descripcion,
        goal = meta,
        pointsAwarded = puntosOtorgados,
        deadline = fechaLimite,
        createdAt = fechaCreacion
    )
}
