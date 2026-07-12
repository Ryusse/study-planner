package com.example.studyplanner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.domain.model.FocusSession
import com.example.studyplanner.domain.usecase.FocusSessionUseCases
import com.example.studyplanner.presentation.state.FocusUiEvent
import com.example.studyplanner.presentation.state.FocusUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FocusViewModel(private val useCases: FocusSessionUseCases) : ViewModel() {
	private val _uiState = MutableStateFlow(FocusUiState())
	val uiState: StateFlow<FocusUiState> = _uiState.asStateFlow()

	private val _event = Channel<FocusUiEvent>()
	val event = _event.receiveAsFlow()

	private var timerJob: kotlinx.coroutines.Job? = null
	private val WORK_DURATION = 25 * 60 * 1000L
	private val BREAK_DURATION = 5 * 60 * 1000L
	private val LONG_BREAK_DURATION = 15 * 60 * 1000L

	init {
		loadSessions()
		loadActiveSession()
		loadStreak()
		loadPoints()
	}

	private fun loadSessions() {
		viewModelScope.launch {
			useCases.getValidated().collect { sessions ->
				_uiState.update { it.copy(sessions = sessions) }
			}
		}
	}

	private fun loadActiveSession() {
		viewModelScope.launch {
			useCases.getActiveSession().collect { session ->
				_uiState.update { it.copy(currentSession = session) }
			}
		}
	}

	private fun loadStreak() {
		viewModelScope.launch {
			useCases.getStreak().collect { streak ->
				_uiState.update { it.copy(streak = streak) }
			}
		}
	}

	private fun loadPoints() {
		viewModelScope.launch {
			useCases.calculatePoints().collect { points ->
				_uiState.update { it.copy(points = points) }
			}
		}
	}

	fun startSession(taskId: Int) {
		if (_uiState.value.currentSession != null) return
		viewModelScope.launch {
			val session = FocusSession(taskId = taskId, cyclesCompleted = 0)
			try {
				useCases.create(session)
				_uiState.update { it.copy(currentSession = session) }
				startTimer(session)
				_event.send(FocusUiEvent.ShowSnackbar("Sesión iniciada"))
			} catch (e: Exception) {
				_event.send(FocusUiEvent.ShowSnackbar("Error: ${e.message}"))
			}
		}
	}

	private fun startTimer(session: FocusSession) {
		timerJob?.cancel()
		timerJob = viewModelScope.launch(Dispatchers.Default) {
			var timeRemaining = WORK_DURATION
			var cyclesCompleted = session.cyclesCompleted

			while (cyclesCompleted < 4) {
				val isWorkPhase = (cyclesCompleted % 2) == 0
				val phaseDuration = if (isWorkPhase) WORK_DURATION else BREAK_DURATION

				timeRemaining = phaseDuration
				while (timeRemaining > 0) {
					_uiState.update { state ->
						state.copy(
							timerMs = timeRemaining,
							currentCycle = cyclesCompleted + 1,
							isWorkPhase = isWorkPhase,
							isLongBreak = false
						)
					}
					delay(1000)
					timeRemaining -= 1000
				}
				cyclesCompleted++
			}

			_uiState.update { it.copy(timerMs = LONG_BREAK_DURATION) }
			delay(LONG_BREAK_DURATION)

			_uiState.update { it.copy(showValidationDialog = true) }
		}
	}

	fun pauseTimer() {
		timerJob?.cancel()
		_uiState.update { it.copy(timerRunning = false) }
	}

	fun resumeTimer(session: FocusSession) {
		_uiState.update { it.copy(timerRunning = true) }
		startTimer(session)
	}

	fun validateSession(session: FocusSession, isValid: Boolean) {
		timerJob?.cancel()
		viewModelScope.launch {
			try {
				if (isValid) {
					val validatedSession = session.copy(
						isValidated = true,
						validatedAt = System.currentTimeMillis(),
						cyclesCompleted = 4
					)
					useCases.validate(validatedSession)
					_event.send(FocusUiEvent.ShowSnackbar("Sesión validada"))
				} else {
					_event.send(FocusUiEvent.ShowSnackbar("Sesión descartada"))
				}
				_uiState.update { it.copy(currentSession = null, showValidationDialog = false) }
				loadSessions()
			} catch (e: Exception) {
				_event.send(FocusUiEvent.ShowSnackbar("Error: ${e.message}"))
			}
		}
	}

	fun abandonSession() {
		timerJob?.cancel()
		_uiState.update { it.copy(currentSession = null, showValidationDialog = false) }
	}
}
