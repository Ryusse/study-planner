package com.example.studyplanner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.domain.usecase.ProfessorUseCases
import com.example.studyplanner.presentation.state.ProfessorUiEvent
import com.example.studyplanner.presentation.state.ProfessorUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfessorViewModel(private val useCases: ProfessorUseCases) : ViewModel() {

	private val _uiState = MutableStateFlow(ProfessorUiState())
	val uiState: StateFlow<ProfessorUiState> = _uiState.asStateFlow()

	private val _event = Channel<ProfessorUiEvent>()
	val event = _event.receiveAsFlow()

	init {
		loadProfessors()
	}

	private fun loadProfessors() {
		viewModelScope.launch {
			useCases.getAll().collect { professors ->
				_uiState.update { it.copy(professors = professors) }
			}
		}
	}

	fun addProfessor(professor: Professor) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(loadingMessage = "Guardando profesor") }
				useCases.add(professor)
				hideAddDialog()
				_event.send(ProfessorUiEvent.ShowSnackbar("Docente agregado correctamente"))
			} catch (e: Exception) {
				_event.send(ProfessorUiEvent.ShowSnackbar("Error: ${e.message}"))
			} finally {
				_uiState.update { it.copy(loadingMessage = null) }
			}
		}
	}

	fun updateProfessor(professor: Professor) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(loadingMessage = "Actualizando profesor") }
				useCases.update(professor)
				hideAddDialog()
				_event.send(ProfessorUiEvent.ShowSnackbar("Docente actualizado correctamente"))
			} catch (e: Exception) {
				_event.send(ProfessorUiEvent.ShowSnackbar("Error: ${e.message}"))
			} finally {
				_uiState.update { it.copy(loadingMessage = null) }
			}
		}
	}

	fun deleteProfessor(professor: Professor) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(loadingMessage = "Eliminando profesor") }
				useCases.delete(professor)
				_event.send(ProfessorUiEvent.ShowSnackbar("Profesor eliminado correctamente"))
			} catch (e: Exception) {
				_event.send(ProfessorUiEvent.ShowSnackbar("Error al eliminar: ${e.message}"))
			} finally {
				_uiState.update { it.copy(loadingMessage = null) }
			}
		}
	}

	fun selectProfessor(professor: Professor) {
		_uiState.update { it.copy(selectedProfessor = professor) }
	}

	fun clearSelection() {
		_uiState.update { it.copy(selectedProfessor = null) }
	}

	fun showAddDialog() {
		_uiState.update { it.copy(showAddDialog = true) }
	}

	fun hideAddDialog() {
		_uiState.update { it.copy(showAddDialog = false, selectedProfessor = null) }
	}
}
