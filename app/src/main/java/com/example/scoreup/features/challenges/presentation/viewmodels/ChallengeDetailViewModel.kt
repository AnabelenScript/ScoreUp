package com.example.scoreup.features.challenges.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.features.challenges.domain.entities.UserChallenge
import com.example.scoreup.features.challenges.domain.usecases.GetChallengeDetailUseCase
import com.example.scoreup.features.challenges.domain.usecases.GetUserChallengeProgressUseCase
import com.example.scoreup.features.challenges.domain.usecases.JoinChallengeUseCase
import com.example.scoreup.features.challenges.domain.usecases.UpdateChallengeProgressUseCase
import com.example.scoreup.features.challenges.presentation.states.ChallengeDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getChallengeDetailUseCase: GetChallengeDetailUseCase,
    private val joinChallengeUseCase: JoinChallengeUseCase,
    private val updateChallengeProgressUseCase: UpdateChallengeProgressUseCase,
    private val getUserChallengeProgressUseCase: GetUserChallengeProgressUseCase
) : ViewModel() {

    private val challengeId: Int = savedStateHandle.get<Int>("challengeId") ?: 0

    private val _uiState = MutableStateFlow(ChallengeDetailState())
    val uiState: StateFlow<ChallengeDetailState> = _uiState.asStateFlow()

    init {
        loadChallengeDetail()
    }

    fun loadChallengeDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val challenge = getChallengeDetailUseCase(challengeId)
                val userChallenge = try {
                    getUserChallengeProgressUseCase(challengeId)
                } catch (_: Exception) {
                    null
                }
                val isCompleted = userChallenge != null &&
                        (userChallenge.status.equals("completado", ignoreCase = true) ||
                                userChallenge.progress >= challenge.meta)
                _uiState.update {
                    it.copy(
                        challenge = challenge,
                        userChallenge = userChallenge,
                        isJoined = userChallenge != null,
                        completed = isCompleted,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Error desconocido")
                }
            }
        }
    }

    fun joinChallenge() {
        viewModelScope.launch {
            _uiState.update { it.copy(isJoining = true, error = null) }
            try {
                joinChallengeUseCase(challengeId)
                val userChallenge = try {
                    getUserChallengeProgressUseCase(challengeId)
                } catch (_: Exception) {
                    UserChallenge(
                        userId = 0,
                        retoId = challengeId,
                        progress = 0,
                        status = "activo",
                        joinedAt = ""
                    )
                }
                _uiState.update {
                    it.copy(
                        isJoining = false,
                        isJoined = true,
                        userChallenge = userChallenge
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isJoining = false, error = e.message ?: "Error al unirse al reto")
                }
            }
        }
    }

    fun incrementProgress() {
        _uiState.update { state ->
            val maxIncrement = (state.challenge?.meta ?: Int.MAX_VALUE) -
                    (state.userChallenge?.progress ?: 0)
            val newIncrement = minOf(state.progressIncrement + 1, maxOf(1, maxIncrement))
            state.copy(progressIncrement = newIncrement)
        }
    }

    fun decrementProgress() {
        _uiState.update {
            it.copy(progressIncrement = maxOf(1, it.progressIncrement - 1))
        }
    }

    fun updateProgress() {
        viewModelScope.launch {
            _uiState.update { it.copy(isUpdatingProgress = true, error = null) }
            try {
                val currentProgress = _uiState.value.userChallenge?.progress ?: 0
                val newProgress = currentProgress + _uiState.value.progressIncrement
                val result = updateChallengeProgressUseCase(challengeId, newProgress)

                val updatedUserChallenge = try {
                    getUserChallengeProgressUseCase(challengeId)
                } catch (_: Exception) {
                    _uiState.value.userChallenge?.copy(progress = newProgress)
                }

                _uiState.update {
                    it.copy(
                        isUpdatingProgress = false,
                        userChallenge = updatedUserChallenge,
                        completed = result.completed,
                        logrosAwarded = result.logrosAwarded,
                        successMessage = result.message,
                        progressIncrement = 1
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isUpdatingProgress = false,
                        error = e.message ?: "Error al actualizar progreso"
                    )
                }
            }
        }
    }

    fun clearMessages() {
        _uiState.update {
            it.copy(successMessage = null, error = null)
        }
    }
}
