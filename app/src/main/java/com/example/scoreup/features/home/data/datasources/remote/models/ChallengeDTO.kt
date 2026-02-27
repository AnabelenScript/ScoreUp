package com.example.scoreup.features.home.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class ChallengeDTO(
    @SerializedName("id_reto") val idReto: Int,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("materia") val materia: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("meta") val meta: String,
    @SerializedName("puntos_otorgados") val puntosOtorgados: Int,
    @SerializedName("fecha_limite") val fechaLimite: String,
    @SerializedName("fecha_creacion") val fechaCreacion: String
)
