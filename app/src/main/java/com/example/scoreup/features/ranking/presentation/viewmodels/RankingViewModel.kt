package com.example.scoreup.features.ranking.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.features.ranking.domain.repositories.RankingRepository
import com.example.scoreup.features.ranking.domain.usecases.GetRankingUseCase
import com.example.scoreup.features.ranking.domain.usecases.ObserveRankingUseCase
import com.example.scoreup.features.ranking.presentation.states.RankingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getRankingUseCase: GetRankingUseCase,
    private val observeRankingUseCase: ObserveRankingUseCase,
    private val repository: RankingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RankingState())
    val uiState: StateFlow<RankingState> = _uiState.asStateFlow()

    init {
        getRanking()
        connectWebSocket()
        observeRealtimeRanking()
    }

    fun getRanking() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val ranking = getRankingUseCase()
                _uiState.update { it.copy(ranking = ranking, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun connectWebSocket() {
        repository.connectWebSocket()
    }

    private fun observeRealtimeRanking() {
        viewModelScope.launch {
            observeRankingUseCase().collect { ranking ->
                _uiState.update { it.copy(ranking = ranking, isLoading = false) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.disconnectWebSocket()
    }
}
