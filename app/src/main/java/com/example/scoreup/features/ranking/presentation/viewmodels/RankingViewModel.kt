package com.example.scoreup.features.ranking.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.features.ranking.domain.usecases.GetRankingUseCase
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
    private val getRankingUseCase: GetRankingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RankingState())
    val uiState: StateFlow<RankingState> = _uiState.asStateFlow()

    init {
        getRanking()
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
}
