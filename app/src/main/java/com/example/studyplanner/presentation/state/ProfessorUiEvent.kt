package com.example.studyplanner.presentation.state

sealed class ProfessorUiEvent {
	data class ShowSnackbar(val message: String) : ProfessorUiEvent()
}
