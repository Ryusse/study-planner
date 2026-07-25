package com.example.studyplanner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.usecase.TaskUseCases
import com.example.studyplanner.presentation.state.TaskStatusFilter
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
				applyFilters()
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

	private fun applyFilters() {
		_uiState.update { state ->
			val filtered = state.tasks
				.filter { state.filterSubjectId == null || it.subjectId == state.filterSubjectId }
				.filter { state.filterPriority == null || it.priority == state.filterPriority }
				.filter {
					when (state.filterStatus) {
						TaskStatusFilter.ALL -> true
						TaskStatusFilter.PENDING -> !it.isCompleted
						TaskStatusFilter.COMPLETED -> it.isCompleted
					}
				}
			state.copy(filteredTasks = filtered)
		}
	}

	fun addTask(task: Task) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(loadingMessage = "Guardando tarea") }
				useCases.create(task)
				hideAddDialog()
				_event.send(TaskUiEvent.ShowSnackbar("Tarea agregada"))
			} catch (e: Exception) {
				_event.send(TaskUiEvent.ShowSnackbar("Error: ${e.message}"))
			} finally {
				_uiState.update { it.copy(loadingMessage = null) }
			}
		}
	}

	fun updateTask(task: Task) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(loadingMessage = "Actualizando tarea") }
				useCases.update(task)
				hideAddDialog()
				_event.send(TaskUiEvent.ShowSnackbar("Tarea actualizada"))
			} catch (e: Exception) {
				_event.send(TaskUiEvent.ShowSnackbar("Error: ${e.message}"))
			} finally {
				_uiState.update { it.copy(loadingMessage = null) }
			}
		}
	}

	fun deleteTask(task: Task) {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(loadingMessage = "Eliminando tarea") }
				useCases.delete(task)
				_event.send(TaskUiEvent.ShowSnackbar("Tarea eliminada"))
			} catch (e: Exception) {
				_event.send(TaskUiEvent.ShowSnackbar("Error: ${e.message}"))
			} finally {
				_uiState.update { it.copy(loadingMessage = null) }
			}
		}
	}

	fun markComplete(task: Task, completed: Boolean) {
		viewModelScope.launch {
			try {
				useCases.markComplete(task, completed)
				_event.send(
					TaskUiEvent.ShowSnackbar(
						if (completed) "Tarea completada" else "Tarea marcada como pendiente"
					)
				)
			} catch (e: Exception) {
				_event.send(TaskUiEvent.ShowSnackbar("Error: ${e.message}"))
			}
		}
	}

	fun selectTask(task: Task) {
		_uiState.update { it.copy(selectedTask = task) }
	}

	fun clearSelection() {
		_uiState.update { it.copy(selectedTask = null) }
	}

	fun showAddDialog() {
		_uiState.update { it.copy(showAddDialog = true) }
	}

	fun hideAddDialog() {
		_uiState.update { it.copy(showAddDialog = false, selectedTask = null) }
	}

	fun setFilterSubject(subjectId: Int?) {
		_uiState.update { it.copy(filterSubjectId = subjectId) }
		applyFilters()
	}

	fun setFilterPriority(priority: String?) {
		_uiState.update { it.copy(filterPriority = priority) }
		applyFilters()
	}

	fun setFilterStatus(status: TaskStatusFilter) {
		_uiState.update { it.copy(filterStatus = status) }
		applyFilters()
	}
}
