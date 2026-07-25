package com.example.studyplanner.presentation.state

sealed class SubjectUiEvent {
	data class ShowSnackbar(val message: String) : SubjectUiEvent()
}
