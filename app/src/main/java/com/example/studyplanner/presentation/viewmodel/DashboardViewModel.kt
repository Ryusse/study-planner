package com.example.studyplanner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.domain.usecase.DashboardUseCases
import com.example.studyplanner.presentation.state.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(private val useCases: DashboardUseCases) : ViewModel() {
	private val _uiState = MutableStateFlow(DashboardUiState())
	val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

	init {
		loadStats()
		loadPendingBySubject()
	}

	private fun loadStats() {
		viewModelScope.launch {
			useCases.getStats().collect { stats ->
				_uiState.update { it.copy(stats = stats) }
			}
		}
	}

	private fun loadPendingBySubject() {
		viewModelScope.launch {
			useCases.getPendingBySubject().collect { pendingBySubject ->
				_uiState.update { it.copy(pendingBySubject = pendingBySubject) }
			}
		}
	}
}
