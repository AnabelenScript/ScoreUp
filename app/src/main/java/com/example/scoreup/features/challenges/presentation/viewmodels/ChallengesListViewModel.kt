package com.example.scoreup.features.challenges.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.features.challenges.presentation.states.ChallengesListState
import com.example.scoreup.features.home.domain.repositories.ChallengeRepository
import com.example.scoreup.features.home.domain.usecases.GetChallengesUseCase
import com.example.scoreup.features.home.domain.usecases.ObserveChallengesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengesListViewModel @Inject constructor(
    private val getChallengesUseCase: GetChallengesUseCase,
    private val observeChallengesUseCase: ObserveChallengesUseCase,
    private val repository: ChallengeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChallengesListState())
    val uiState: StateFlow<ChallengesListState> = _uiState.asStateFlow()

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
