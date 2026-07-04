package com.example.studyplanner.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studyplanner.presentation.screens.dashboard.components.TaskStatsCard
import com.example.studyplanner.presentation.screens.dashboard.components.TasksBySubjectCard
import com.example.studyplanner.presentation.viewmodel.TaskViewModel

@Composable
fun DashboardScreen(
	navController: NavHostController,
	taskViewModel: TaskViewModel,
	modifier: Modifier = Modifier
) {
	val taskState by taskViewModel.uiState.collectAsState()
	
	Column(
		modifier = modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.padding(16.dp)
			.verticalScroll(rememberScrollState()),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		TaskStatsCard(
			totalTasks = taskState.tasks.size,
			completedTasks = taskState.tasks.count { it.isCompleted },
			pendingTasks = taskState.pendingCount
		)
		TasksBySubjectCard(
			tasks = taskState.tasks,
			navController = navController
		)
	}
}
