package com.example.scoreup.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.features.home.domain.repositories.ChallengeRepository
import com.example.scoreup.features.home.domain.usecases.GetChallengesUseCase
import com.example.scoreup.features.home.domain.usecases.ObserveChallengesUseCase
import com.example.scoreup.features.home.presentation.states.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChallengesUseCase: GetChallengesUseCase,
    private val observeChallengesUseCase: ObserveChallengesUseCase,
    private val repository: ChallengeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        getChallenges()
        connectWebSocket()
        observeRealtimeChallenges()
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
        repository.connectWebSocket()
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
        repository.disconnectWebSocket()
    }
}
