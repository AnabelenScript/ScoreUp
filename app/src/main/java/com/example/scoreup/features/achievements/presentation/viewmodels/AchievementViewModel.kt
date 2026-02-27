package com.example.scoreup.features.achievements.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val getAchievementsUseCase: GetAchievementsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AchievementState())
    val uiState: StateFlow<AchievementState> = _uiState.asStateFlow()

    init {
        getAchievements()
    }

    fun getAchievements() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val achievements = getAchievementsUseCase()
                _uiState.update { it.copy(achievements = achievements, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
