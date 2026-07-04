package com.example.studyplanner.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studyplanner.domain.model.Task

@Composable
fun TaskCard(
	task: Task,
	modifier: Modifier = Modifier,
	onClick: () -> Unit = {}
) {
	Card(
		modifier = modifier,
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
		onClick = onClick
	) {
		Text(
			text = task.title,
			style = MaterialTheme.typography.titleLarge,
			modifier = Modifier.padding(16.dp)
		)
		Text(
			text = "Prioridad: ${task.priority}",
			style = MaterialTheme.typography.bodyLarge,
			modifier = Modifier.padding(16.dp)
		)
	}
}
