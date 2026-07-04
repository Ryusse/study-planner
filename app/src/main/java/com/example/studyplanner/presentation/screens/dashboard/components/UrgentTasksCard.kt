package com.example.studyplanner.presentation.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studyplanner.core.navigation.NavRoutes
import com.example.studyplanner.domain.model.Task

@Composable
fun UrgentTasksCard(
	urgentTasks: List<Task>,
	navController: NavHostController
) {
	Card(modifier = Modifier.fillMaxWidth()) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Text("3 Más urgentes", fontSize = 14.sp, fontWeight = FontWeight.Bold)
			urgentTasks.take(3).forEach { task ->
				Text(
					"• ${task.title}",
					fontSize = 12.sp,
					modifier = Modifier.padding(vertical = 4.dp)
				)
			}
			Button(
				onClick = { navController.navigate(NavRoutes.TASK_LIST) },
				modifier = Modifier.align(Alignment.End)
			) {
				Text("Ver todas")
			}
		}
	}
}
