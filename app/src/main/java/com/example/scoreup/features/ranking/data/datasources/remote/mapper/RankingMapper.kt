package com.example.scoreup.features.ranking.data.datasources.remote.mapper

import com.example.scoreup.features.ranking.data.datasources.remote.models.RankingUserDTO
import com.example.scoreup.features.ranking.domain.entities.RankingUser

fun RankingUserDTO.toDomain(posicion: Int, esUsuarioActual: Boolean = false): RankingUser {
    return RankingUser(
        idUsuario = id,
        nombre = name,
        puntos = totalScore,
        posicion = posicion,
        tendencia = 0,
        esUsuarioActual = esUsuarioActual
    )
}

fun List<RankingUserDTO>.toDomainList(currentUserId: Int?): List<RankingUser> {
    return mapIndexed { index, dto ->
        dto.toDomain(
            posicion = index + 1,
            esUsuarioActual = dto.id == currentUserId
        )
    }
}
