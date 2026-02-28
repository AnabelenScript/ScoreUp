package com.example.scoreup.features.achievements.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

/**
 * DTO para un logro del catálogo (GET /api/logros)
 */
data class AchievementDTO(
    @SerializedName("ID") val id: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Description") val description: String,
    @SerializedName("RequiredPoints") val requiredPoints: Int,
    @SerializedName("RequiredRetos") val requiredRetos: Int
)

/**
 * Wrapper para GET /api/logros → {"logros": [...]}
 */
data class AchievementsResponseDTO(
    @SerializedName("logros") val logros: List<AchievementDTO>
)

/**
 * DTO para un logro obtenido por el usuario (GET /api/usuario-logros)
 */
data class UserAchievementDTO(
    @SerializedName("UserID") val userId: Int,
    @SerializedName("LogroID") val logroId: Int,
    @SerializedName("ObtainedAt") val obtainedAt: String
)

/**
 * Wrapper para GET /api/usuario-logros → {"usuario_logros": [...]}
 */
data class UserAchievementsResponseDTO(
    @SerializedName("usuario_logros") val usuarioLogros: List<UserAchievementDTO>
)

/**
 * DTO para logro otorgado en evaluación
 */
data class AwardedLogroDTO(
    @SerializedName("ID") val id: Int,
    @SerializedName("Name") val name: String
)

/**
 * Wrapper para POST /api/usuario-logros/evaluate
 */
data class EvaluateAchievementsResponseDTO(
    @SerializedName("message") val message: String,
    @SerializedName("logros_awarded") val logrosAwarded: List<AwardedLogroDTO>
)
