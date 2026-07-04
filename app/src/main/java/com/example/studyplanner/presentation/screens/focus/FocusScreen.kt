package com.example.studyplanner.presentation.screens.focus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studyplanner.presentation.screens.focus.components.PomodoroTimerUI
import com.example.studyplanner.presentation.screens.focus.components.ValidationDialog
import com.example.studyplanner.presentation.viewmodel.FocusViewModel

@Composable
fun FocusScreen(
	navController: NavHostController,
	viewModel: FocusViewModel,
	modifier: Modifier = Modifier
) {
	val state by viewModel.uiState.collectAsState()

	Scaffold(modifier = modifier) { paddingValues ->
		if (state.currentSession == null) {
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(paddingValues)
					.padding(16.dp),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text("No hay sesión activa", fontSize = 16.sp)
				Button(onClick = { navController.popBackStack() }) {
					Text("Volver")
				}
			}
		} else {
			PomodoroTimerUI(
				state = state,
				onPause = { viewModel.pauseTimer() },
				onResume = { state.currentSession?.let { viewModel.resumeTimer(it) } },
				onAbandon = { viewModel.abandonSession() },
				paddingValues = paddingValues
			)
		}
	}

	if (state.showValidationDialog && state.currentSession != null) {
		ValidationDialog(
			onYes = { viewModel.validateSession(state.currentSession!!, true) },
			onNo = { viewModel.validateSession(state.currentSession!!, false) }
		)
	}
}
