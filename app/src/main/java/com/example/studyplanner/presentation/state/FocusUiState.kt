package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.FocusSession

data class FocusUiState(
	val sessions: List<FocusSession> = emptyList(),
	val isLoading: Boolean = false,
	val error: String? = null,
	val currentSession: FocusSession? = null,
	val streak: Int = 0,
	val points: Int = 0,
	val timerMs: Long = 0L,
	val currentCycle: Int = 0,
	val isWorkPhase: Boolean = true,
	val isLongBreak: Boolean = false,
	val timerRunning: Boolean = false,
	val showValidationDialog: Boolean = false
)
