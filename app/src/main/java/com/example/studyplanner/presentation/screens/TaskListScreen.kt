package com.example.studyplanner.presentation.screens

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.studyplanner.presentation.viewmodel.TaskViewModel

@Composable
fun TaskListScreen(
	navController: NavHostController,
	viewModel: TaskViewModel,
	modifier: Modifier = Modifier
) {
	Scaffold(modifier = modifier) { paddingValues ->
		Text("Task List - Pendiente implementar")
	}
}
