package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Subject

sealed class SubjectUiEvent {
	data class ShowSnackbar(val message: String) : SubjectUiEvent()
	data class NavigateTo(val route: String) : SubjectUiEvent()
	data object NavigateBack : SubjectUiEvent()
	data class SelectSubject(val subject: Subject) : SubjectUiEvent()
	data class DeleteSubject(val subject: Subject) : SubjectUiEvent()
}
