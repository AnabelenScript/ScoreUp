package com.example.scoreup.features.challenges.data.datasources.remote.mapper

import com.example.scoreup.features.challenges.data.datasources.remote.models.AwardedLogroDTO
import com.example.scoreup.features.challenges.data.datasources.remote.models.ChallengeDTO
import com.example.scoreup.features.challenges.data.datasources.remote.models.UserChallengeDTO
import com.example.scoreup.features.challenges.domain.entities.AwardedLogro
import com.example.scoreup.features.challenges.domain.entities.UserChallenge
import com.example.scoreup.features.home.domain.entities.Challenge

fun ChallengeDTO.toDetailDomain(): Challenge {
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

fun UserChallengeDTO.toDomain(): UserChallenge {
    return UserChallenge(
        userId = userId,
        retoId = retoId,
        progress = progress,
        status = status,
        joinedAt = joinedAt
    )
}

fun AwardedLogroDTO.toDomain(): AwardedLogro {
    return AwardedLogro(
        id = id,
        name = name
    )
}
