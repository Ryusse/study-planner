package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Subject

data class SubjectUiState(
	val subjects: List<Subject> = emptyList(),
	val loadingMessage: String? = null,
	val selectedSubject: Subject? = null,
	val showAddDialog: Boolean = false
)
