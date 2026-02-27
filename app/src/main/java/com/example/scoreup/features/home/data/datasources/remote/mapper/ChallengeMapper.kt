package com.example.scoreup.features.home.data.datasources.remote.mapper

import com.example.scoreup.features.home.data.datasources.remote.models.ChallengeDTO
import com.example.scoreup.features.home.domain.entities.Challenge

fun ChallengeDTO.toDomain(): Challenge {
    return Challenge(
        idReto = idReto,
        idUsuario = idUsuario,
        materia = materia,
        descripcion = descripcion,
        meta = meta,
        puntosOtorgados = puntosOtorgados,
        fechaLimite = fechaLimite,
        fechaCreacion = fechaCreacion
    )
}
