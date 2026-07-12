package com.example.studyplanner.presentation.state

sealed class TaskUiEvent {
	data class ShowSnackbar(val message: String) : TaskUiEvent()
}
