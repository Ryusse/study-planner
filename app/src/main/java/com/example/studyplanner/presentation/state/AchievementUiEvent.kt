package com.example.studyplanner.presentation.state

sealed class AchievementUiEvent {
	data class ShowSnackbar(val message: String) : AchievementUiEvent()
	data class NavigateTo(val route: String) : AchievementUiEvent()
	data object NavigateBack : AchievementUiEvent()
	data object UnlockAchievement : AchievementUiEvent()
}
