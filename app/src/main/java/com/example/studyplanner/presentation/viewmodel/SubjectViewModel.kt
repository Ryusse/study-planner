package com.example.studyplanner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.usecase.SubjectUseCases
import com.example.studyplanner.presentation.state.SubjectUiEvent
import com.example.studyplanner.presentation.state.SubjectUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubjectViewModel(private val useCases: SubjectUseCases) : ViewModel() {
	private val _uiState = MutableStateFlow(SubjectUiState())
	val uiState: StateFlow<SubjectUiState> = _uiState.asStateFlow()

	private val _event = Channel<SubjectUiEvent>()
	val event = _event.receiveAsFlow()

	init {
		loadSubjects()
	}

	private fun loadSubjects() {
		viewModelScope.launch {
			useCases.getAll().collect { subjects ->
				_uiState.update { it.copy(subjects = subjects) }
			}
		}
	}

	fun addSubject(subject: Subject) {
		viewModelScope.launch {
			try {
				useCases.add(subject)
				_event.send(SubjectUiEvent.ShowSnackbar("Materia agregada"))
			} catch (e: Exception) {
				_event.send(SubjectUiEvent.ShowSnackbar("Error: ${e.message}"))
			}
		}
	}

	fun deleteSubject(subject: Subject) {
		viewModelScope.launch {
			try {
				useCases.delete(subject)
				_event.send(SubjectUiEvent.ShowSnackbar("Materia eliminada"))
			} catch (e: Exception) {
				_event.send(SubjectUiEvent.ShowSnackbar("Error: ${e.message}"))
			}
		}
	}
}
