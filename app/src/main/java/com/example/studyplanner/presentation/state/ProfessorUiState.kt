package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Professor

data class ProfessorUiState(
	val professors: List<Professor> = emptyList(),
	val loadingMessage: String? = null,
	val selectedProfessor: Professor? = null,
	val showAddDialog: Boolean = false
)
