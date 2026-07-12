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
				_uiState.update { it.copy(loadingMessage = "Guardando materia") }
				useCases.add(subject)
				hideAddDialog()
				_event.send(SubjectUiEvent.ShowSnackbar("Materia agregada"))
			} catch (e: Exception) {
				_event.send(SubjectUiEvent.ShowSnackbar("Error: ${e.message}"))
			} finally {
				_uiState.update { it.copy(loadingMessage = null) }
			}
		}
	}

	fun updateSubject(subject: Subject) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(loadingMessage = "Actualizando materia") }
				useCases.update(subject)
				hideAddDialog()
				_event.send(SubjectUiEvent.ShowSnackbar("Materia actualizada"))
			} catch (e: Exception) {
				_event.send(SubjectUiEvent.ShowSnackbar("Error: ${e.message}"))
			} finally {
				_uiState.update { it.copy(loadingMessage = null) }
			}
		}
	}

	fun deleteSubject(subject: Subject) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(loadingMessage = "Eliminando materia") }
				useCases.delete(subject)
				_event.send(SubjectUiEvent.ShowSnackbar("Materia eliminada"))
			} catch (e: Exception) {
				_event.send(SubjectUiEvent.ShowSnackbar("Error al eliminar: ${e.message}"))
			} finally {
				_uiState.update { it.copy(loadingMessage = null) }
			}
		}
	}

	fun selectSubject(subject: Subject) {
		_uiState.update { it.copy(selectedSubject = subject) }
	}

	fun clearSelection() {
		_uiState.update { it.copy(selectedSubject = null) }
	}

	fun showAddDialog() {
		_uiState.update { it.copy(showAddDialog = true) }
	}

	fun hideAddDialog() {
		_uiState.update { it.copy(showAddDialog = false, selectedSubject = null) }
	}
}
