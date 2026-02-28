package com.example.scoreup.features.challenges.domain.entities

data class Challenge(
    val idReto: Int,
    val idUsuario: Int,
    val materia: String,
    val descripcion: String,
    val meta: Int,
    val puntosOtorgados: Int,
    val fechaLimite: String,
    val fechaCreacion: String
)
