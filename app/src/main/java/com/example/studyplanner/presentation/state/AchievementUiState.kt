package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Achievement

data class AchievementUiState(
	val achievements: List<Achievement> = emptyList(),
	val isLoading: Boolean = false,
	val error: String? = null,
	val unlockedCount: Int = 0
)
