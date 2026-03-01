package com.example.scoreup.features.challenges.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.core.storage.TokenManager
import com.example.scoreup.features.challenges.domain.entities.Challenge
import com.example.scoreup.features.challenges.domain.usecases.CreateChallengeUseCase
import com.example.scoreup.features.challenges.presentation.states.CreateChallengeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val createChallengeUseCase: CreateChallengeUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateChallengeState())
    val uiState = _uiState.asStateFlow()

    fun onSubjectChange(subject: String) {
        _uiState.update { it.copy(subject = subject) }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onGoalChange(goal: String) {
        if (goal.isEmpty() || goal.all { it.isDigit() }) {
            _uiState.update { it.copy(goal = goal) }
        }
    }

    fun onPointsChange(points: String) {
        if (points.isEmpty() || points.all { it.isDigit() }) {
            _uiState.update { it.copy(points = points) }
        }
    }

    fun onDeadlineChange(deadline: String) {
        _uiState.update { it.copy(deadline = deadline) }
    }

    fun createChallenge() {
        val state = _uiState.value

        if (state.subject.isBlank() || state.description.isBlank() || 
            state.goal.isBlank() || state.points.isBlank() || state.deadline.isBlank()) {
            Log.w("CreateChallenge", "Validación fallida: Hay campos vacíos")
            _uiState.update { it.copy(error = "Por favor, completa todos los campos") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val userId = tokenManager.getUserId() ?: throw Exception("Usuario no autenticado")
                val now = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date())
                val newChallenge = Challenge(
                    idReto = 0,
                    idUsuario = userId,
                    materia = state.subject,
                    descripcion = state.description,
                    meta = state.goal.toInt(),
                    puntosOtorgados = state.points.toInt(),
                    fechaLimite = state.deadline,
                    fechaCreacion = now
                )
                
                Log.d("CreateChallenge", "Enviando al caso de uso: $newChallenge")
                val result = createChallengeUseCase(newChallenge)
                Log.d("CreateChallenge", "Reto creado exitosamente: $result")
                
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                Log.e("CreateChallenge", "Error durante la creación", e)
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al crear el reto") }
            }
        }
    }
    
    fun resetSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}
