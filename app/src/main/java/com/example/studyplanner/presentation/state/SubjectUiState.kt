package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Subject

data class SubjectUiState(
	val subjects: List<Subject> = emptyList(),
	val isLoading: Boolean = false,
	val error: String? = null,
	val selectedSubject: Subject? = null
)
