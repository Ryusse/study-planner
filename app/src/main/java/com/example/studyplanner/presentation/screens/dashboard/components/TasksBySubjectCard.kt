package com.example.studyplanner.presentation.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun TasksBySubjectCard(
	tasks: List<Task>,
	navController: NavHostController
) {
	val pendingTasks = tasks.filter { !it.isCompleted }
	val tasksBySubject = pendingTasks.groupBy { it.subjectId }

	Card(
		modifier = Modifier.fillMaxWidth(),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceContainer
		)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Text(
				"Tareas Pendientes por Materia",
				fontSize = 16.sp,
				fontWeight = FontWeight.Bold,
				modifier = Modifier.padding(bottom = 8.dp)
			)

			if (tasksBySubject.isEmpty()) {
				Text(
					"No hay tareas pendientes",
					fontSize = 14.sp,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					modifier = Modifier.padding(vertical = 16.dp)
				)
			} else {
				tasksBySubject.forEach { (_, tasksForSubject) ->
					SubjectTaskRow(
						subjectName = "Materia ${tasksForSubject.first().subjectId}",
						pendingCount = tasksForSubject.size
					)
				}
			}

			Button(
				onClick = {
					navController.navigate(NavRoutes.TASK_LIST)
				},
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 8.dp)
			) {
				Text("Ver Todas las Tareas")
			}
		}
	}
}

@Composable
private fun SubjectTaskRow(
	subjectName: String,
	pendingCount: Int
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(8.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			subjectName,
			fontSize = 14.sp,
			modifier = Modifier.weight(1f)
		)
		Row(
			horizontalArrangement = Arrangement.spacedBy(4.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				"$pendingCount pendiente${if (pendingCount != 1) "s" else ""}",
				fontSize = 14.sp,
				fontWeight = FontWeight.SemiBold,
				color = MaterialTheme.colorScheme.primary
			)
			Icon(
				Icons.Default.ChevronRight,
				contentDescription = null,
				tint = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
	}
}
