package com.example.scoreup.features.achievements.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.features.achievements.domain.usecases.EvaluateAchievementsUseCase
import com.example.scoreup.features.achievements.domain.usecases.GetAchievementsUseCase
import com.example.scoreup.features.achievements.presentation.states.AchievementState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val evaluateAchievementsUseCase: EvaluateAchievementsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AchievementState())
    val uiState: StateFlow<AchievementState> = _uiState.asStateFlow()

    init {
        loadAchievements()
    }

    fun loadAchievements() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // Primero evaluar logros pendientes, luego cargar la lista completa
                val achievements = evaluateAchievementsUseCase()
                _uiState.update { it.copy(achievements = achievements, isLoading = false) }
            } catch (e: Exception) {
                // Si falla la evaluación, intentar solo cargar los logros
                try {
                    val achievements = getAchievementsUseCase()
                    _uiState.update { it.copy(achievements = achievements, isLoading = false) }
                } catch (e2: Exception) {
                    _uiState.update { it.copy(isLoading = false, error = e2.message) }
                }
            }
        }
    }
}
