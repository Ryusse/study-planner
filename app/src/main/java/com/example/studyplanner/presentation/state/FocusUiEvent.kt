package com.example.studyplanner.presentation.state

sealed class FocusUiEvent {
	data class ShowSnackbar(val message: String) : FocusUiEvent()
	data class NavigateTo(val route: String) : FocusUiEvent()
	data object NavigateBack : FocusUiEvent()
	data object StartSession : FocusUiEvent()
	data object EndSession : FocusUiEvent()
	data object ValidateSession : FocusUiEvent()
}
