package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Task

sealed class TaskUiEvent {
	data class ShowSnackbar(val message: String) : TaskUiEvent()
	data class NavigateTo(val route: String) : TaskUiEvent()
	data object NavigateBack : TaskUiEvent()
	data class SelectTask(val task: Task) : TaskUiEvent()
	data class DeleteTask(val task: Task) : TaskUiEvent()
}
