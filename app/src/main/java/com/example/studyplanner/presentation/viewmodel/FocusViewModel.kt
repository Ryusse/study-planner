package com.example.studyplanner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.domain.model.FocusSession
import com.example.studyplanner.domain.model.PomodoroPolicy
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

	private data class Phase(
		val cycle: Int,
		val isWork: Boolean,
		val isLong: Boolean,
		val duration: Long
	)

	private val phases = listOf(
		Phase(1, isWork = true, isLong = false, duration = PomodoroPolicy.WORK_DURATION_MS),
		Phase(1, isWork = false, isLong = false, duration = PomodoroPolicy.BREAK_DURATION_MS),
		Phase(2, isWork = true, isLong = false, duration = PomodoroPolicy.WORK_DURATION_MS),
		Phase(2, isWork = false, isLong = false, duration = PomodoroPolicy.BREAK_DURATION_MS),
		Phase(3, isWork = true, isLong = false, duration = PomodoroPolicy.WORK_DURATION_MS),
		Phase(3, isWork = false, isLong = false, duration = PomodoroPolicy.BREAK_DURATION_MS),
		Phase(4, isWork = true, isLong = false, duration = PomodoroPolicy.WORK_DURATION_MS),
		Phase(4, isWork = false, isLong = true, duration = PomodoroPolicy.LONG_BREAK_DURATION_MS)
	)
	
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
				if (session != null) useCases.delete(session)
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
		val session = FocusSession(taskId = taskId, cyclesCompleted = 0)
		_uiState.update { it.copy(currentSession = session, timerRunning = true) }
		runCountdown(phaseIndex = 0, timeRemaining = phases[0].duration)
		viewModelScope.launch { _event.send(FocusUiEvent.ShowSnackbar("Sesión iniciada")) }
	}
	
	private fun runCountdown(phaseIndex: Int, timeRemaining: Long) {
		timerJob?.cancel()
		timerJob = viewModelScope.launch(Dispatchers.Default) {
			var index = phaseIndex
			var remaining = timeRemaining
			
			while (index < phases.size) {
				val phase = phases[index]
				val phaseEndTime = System.currentTimeMillis() + remaining
				while (System.currentTimeMillis() < phaseEndTime) {
					val remainingNow = phaseEndTime - System.currentTimeMillis()
					_uiState.update { state ->
						state.copy(
							timerMs = remainingNow,
							currentCycle = phase.cycle,
							isWorkPhase = phase.isWork,
							isLongBreak = phase.isLong,
							phaseIndex = index
						)
					}
					delay(1000)
				}
				index++
				remaining = phases.getOrNull(index)?.duration ?: 0L
			}
			
			_uiState.update { it.copy(showValidationDialog = true) }
		}
	}
	
	private fun resetTimerState() {
		_uiState.update {
			it.copy(
				currentSession = null,
				timerMs = 0L,
				currentCycle = 0,
				isWorkPhase = true,
				isLongBreak = false,
				timerRunning = false,
				showValidationDialog = false,
				phaseIndex = 0
			)
		}
	}

	fun pauseTimer() {
		timerJob?.cancel()
		_uiState.update { it.copy(timerRunning = false) }
	}
	
	fun resumeTimer(session: FocusSession) {
		val state = _uiState.value
		_uiState.update { it.copy(timerRunning = true) }
		runCountdown(
			phaseIndex = state.phaseIndex,
			timeRemaining = if (state.timerMs > 0) state.timerMs else phases[state.phaseIndex].duration
		)
	}
	
	fun validateSession(session: FocusSession, isValid: Boolean) {
		timerJob?.cancel()
		viewModelScope.launch {
			try {
				if (isValid) {
					val validatedSession = session.copy(
						isValidated = true,
						validatedAt = System.currentTimeMillis(),
						cyclesCompleted = PomodoroPolicy.TOTAL_CYCLES
					)
					useCases.create(validatedSession)
					_event.send(FocusUiEvent.ShowSnackbar("Sesión validada"))
				} else {
					useCases.delete(session)
					_event.send(FocusUiEvent.ShowSnackbar("Sesión descartada"))
				}
				resetTimerState()
				loadSessions()
			} catch (e: Exception) {
				_event.send(FocusUiEvent.ShowSnackbar("Error: ${e.message}"))
			}
		}
	}
	
	fun abandonSession() {
		timerJob?.cancel()
		val state = _uiState.value
		val session = state.currentSession
		val completedCycles = if (state.isWorkPhase) state.currentCycle - 1 else state.currentCycle
		resetTimerState()
		if (session == null) return

		viewModelScope.launch {
			if (completedCycles > 0) {
				val partialSession = session.copy(
					isValidated = true,
					validatedAt = System.currentTimeMillis(),
					cyclesCompleted = completedCycles
				)
				useCases.create(partialSession)
				_event.send(FocusUiEvent.ShowSnackbar("Sesión guardada: $completedCycles de ${PomodoroPolicy.TOTAL_CYCLES} ciclos"))
			} else {
				useCases.delete(session)
			}
		}
	}
}
