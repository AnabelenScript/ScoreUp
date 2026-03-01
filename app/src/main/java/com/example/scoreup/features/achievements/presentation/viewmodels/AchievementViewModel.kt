package com.example.scoreup.features.achievements.presentation.viewmodels

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.core.storage.TokenManager
import com.example.scoreup.features.achievements.domain.usecases.EvaluateAchievementsUseCase
import com.example.scoreup.features.achievements.domain.usecases.GetAchievementsUseCase
import com.example.scoreup.features.achievements.presentation.states.AchievementState
import com.example.scoreup.features.home.domain.repositories.ChallengeRepository
import com.example.scoreup.features.ranking.domain.repositories.RankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val evaluateAchievementsUseCase: EvaluateAchievementsUseCase,
    private val rankingRepository: RankingRepository,
    private val challengeRepository: ChallengeRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AchievementState())
    val uiState: StateFlow<AchievementState> = _uiState.asStateFlow()

    init {
        loadAchievements()
        loadUserStats()
    }

    private fun loadAchievements() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val achievements = evaluateAchievementsUseCase()
                val unlockedCount = achievements.count { it.isUnlocked }
                _uiState.update { it.copy(
                    achievements = achievements,
                    totalUnlocked = unlockedCount,
                    totalAchievements = achievements.size,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                try {
                    val achievements = getAchievementsUseCase()
                    val unlockedCount = achievements.count { it.isUnlocked }
                    _uiState.update { it.copy(
                        achievements = achievements,
                        totalUnlocked = unlockedCount,
                        totalAchievements = achievements.size,
                        isLoading = false
                    ) }
                } catch (e2: Exception) {
                    _uiState.update { it.copy(isLoading = false, error = e2.message) }
                }
            }
        }
    }

    private fun loadUserStats() {
        viewModelScope.launch {
            try {
                val userId = tokenManager.getUserId() ?: return@launch
                val token = tokenManager.getToken() ?: return@launch

                // Puntos desde el ranking
                val rankingList = rankingRepository.getRanking()
                val userRank = rankingList.find { it.idUsuario == userId }

                // Retos completados
                val challenges = challengeRepository.getAllChallenges()
                val completedCount = challenges.count { it.fechaLimite == "Completado" }

                // Antigüedad desde el token
                val payload = decodeJwtPayload(token)
                val createdAt = payload.optString("created_at", "")
                val seniority = calculateSeniority(createdAt)

                _uiState.update { it.copy(
                    totalPoints = userRank?.puntos?.toString() ?: "0",
                    completedChallenges = completedCount.toString(),
                    seniority = seniority
                ) }
            } catch (e: Exception) {
                // Ignore stats errors
            }
        }
    }

    private fun calculateSeniority(createdAt: String): String {
        if (createdAt.isBlank()) return "1 día"
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val creationDate = sdf.parse(createdAt.substringBefore("T")) ?: return "1 día"
            val today = Date()
            val diff = today.time - creationDate.time
            val days = (diff / (1000 * 60 * 60 * 24)).toInt()
            if (days <= 0) "1 día" else "$days días"
        } catch (e: Exception) {
            "1 día"
        }
    }

    private fun decodeJwtPayload(jwt: String): JSONObject {
        val parts = jwt.split(".")
        if (parts.size < 2) return JSONObject()
        val payloadBytes = Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
        return JSONObject(String(payloadBytes, Charsets.UTF_8))
    }
}
