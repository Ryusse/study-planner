package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Task

data class TaskUiState(
	val tasks: List<Task> = emptyList(),
	val pendingTasks: List<Task> = emptyList(),
	val urgentTasks: List<Task> = emptyList(),
	val pendingCount: Int = 0,
	val isLoading: Boolean = false,
	val error: String? = null,
	val selectedTask: Task? = null,
	val showLoadingDialog: Boolean = false,
	val showDeleteDialog: Boolean = false,
	val showAddEditSheet: Boolean = false
)
