package com.example.studyplanner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.usecase.TaskUseCases
import com.example.studyplanner.presentation.state.TaskUiEvent
import com.example.studyplanner.presentation.state.TaskUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(private val useCases: TaskUseCases) : ViewModel() {
	private val _uiState = MutableStateFlow(TaskUiState())
	val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

	private val _event = Channel<TaskUiEvent>()
	val event = _event.receiveAsFlow()

	init {
		loadTasks()
		loadUrgentTasks()
		loadPendingCount()
	}

	private fun loadTasks() {
		viewModelScope.launch {
			useCases.getAll().collect { tasks ->
				_uiState.update { it.copy(tasks = tasks) }
			}
		}
	}

	private fun loadUrgentTasks() {
		viewModelScope.launch {
			useCases.getUrgent().collect { tasks ->
				_uiState.update { it.copy(urgentTasks = tasks) }
			}
		}
	}

	private fun loadPendingCount() {
		viewModelScope.launch {
			useCases.getPending().collect { tasks ->
				_uiState.update { it.copy(pendingCount = tasks.size) }
			}
		}
	}

	fun addTask(task: Task) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(showLoadingDialog = true) }
				useCases.create(task)
				_uiState.update { it.copy(showAddEditSheet = false) }
				_event.send(TaskUiEvent.ShowSnackbar("Tarea agregada"))
			} catch (e: Exception) {
				_event.send(TaskUiEvent.ShowSnackbar("Error: ${e.message}"))
			} finally {
				_uiState.update { it.copy(showLoadingDialog = false) }
			}
		}
	}

	fun deleteTask(task: Task) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(showLoadingDialog = true) }
				useCases.delete(task)
				_uiState.update { it.copy(showDeleteDialog = false) }
				_event.send(TaskUiEvent.ShowSnackbar("Tarea eliminada"))
			} catch (e: Exception) {
				_event.send(TaskUiEvent.ShowSnackbar("Error: ${e.message}"))
			} finally {
				_uiState.update { it.copy(showLoadingDialog = false) }
			}
		}
	}

	fun showDeleteDialog() {
		_uiState.update { it.copy(showDeleteDialog = true) }
	}

	fun dismissDeleteDialog() {
		_uiState.update { it.copy(showDeleteDialog = false) }
	}

	fun showAddEditSheet() {
		_uiState.update { it.copy(showAddEditSheet = true) }
	}

	fun dismissAddEditSheet() {
		_uiState.update { it.copy(showAddEditSheet = false) }
	}
}
