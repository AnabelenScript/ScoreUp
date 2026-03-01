package com.example.scoreup.features.home.presentation.viewmodels

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.core.storage.TokenManager
import com.example.scoreup.features.home.domain.repositories.ChallengeRepository
import com.example.scoreup.features.home.domain.usecases.GetChallengesUseCase
import com.example.scoreup.features.home.domain.usecases.ObserveChallengesUseCase
import com.example.scoreup.features.home.presentation.states.HomeState
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
class HomeViewModel @Inject constructor(
    private val getChallengesUseCase: GetChallengesUseCase,
    private val observeChallengesUseCase: ObserveChallengesUseCase,
    private val challengeRepository: ChallengeRepository,
    private val rankingRepository: RankingRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        loadUserData()
        getChallenges()
        loadUserRanking()
        connectWebSocket()
        observeRealtimeChallenges()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val token = tokenManager.getToken()
            if (token != null) {
                try {
                    val payload = decodeJwtPayload(token)
                    val name = payload.optString("name", "Usuario")
                    val createdAt = payload.optString("created_at", "")
                    
                    val seniority = calculateSeniority(createdAt)
                    
                    _uiState.update { it.copy(userName = name, seniority = seniority) }
                } catch (e: Exception) {
                    _uiState.update { it.copy(userName = "Usuario") }
                }
            }
        }
    }

    private fun loadUserRanking() {
        viewModelScope.launch {
            try {
                val userId = tokenManager.getUserId() ?: return@launch
                val rankingList = rankingRepository.getRanking()
                val userRank = rankingList.find { it.idUsuario == userId }

                if (userRank != null) {
                    _uiState.update { it.copy(
                        points = userRank.puntos,
                        ranking = userRank.posicion
                    ) }
                }
            } catch (e: Exception) {
                // Silently fail for ranking
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

    fun getChallenges() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val challenges = getChallengesUseCase()
                _uiState.update { it.copy(challenges = challenges, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error desconocido") }
            }
        }
    }

    private fun connectWebSocket() {
        challengeRepository.connectWebSocket()
    }

    private fun observeRealtimeChallenges() {
        viewModelScope.launch {
            observeChallengesUseCase().collect { challenges ->
                _uiState.update { it.copy(challenges = challenges, isLoading = false) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        challengeRepository.disconnectWebSocket()
    }
}
