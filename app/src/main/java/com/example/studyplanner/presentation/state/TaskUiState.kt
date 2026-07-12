package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Task

enum class TaskStatusFilter {
	ALL, PENDING, COMPLETED
}

data class TaskUiState(
	val tasks: List<Task> = emptyList(),
	val filteredTasks: List<Task> = emptyList(),
	val urgentTasks: List<Task> = emptyList(),
	val pendingCount: Int = 0,
	val filterSubjectId: Int? = null,
	val filterPriority: String? = null,
	val filterStatus: TaskStatusFilter = TaskStatusFilter.ALL,
	val loadingMessage: String? = null,
	val selectedTask: Task? = null,
	val showAddDialog: Boolean = false
)
