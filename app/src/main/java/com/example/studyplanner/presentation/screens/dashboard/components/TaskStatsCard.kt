package com.example.studyplanner.presentation.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskStatsCard(
	totalTasks: Int,
	completedTasks: Int,
	pendingTasks: Int
) {
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
				"Estado General",
				fontSize = 16.sp,
				fontWeight = FontWeight.Bold
			)

			Row(
				modifier = Modifier
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				StatItem(label = "Total", value = totalTasks.toString())
				StatItem(label = "Completadas", value = completedTasks.toString())
				StatItem(label = "Pendientes", value = pendingTasks.toString())
			}
		}
	}
}

@Composable
private fun StatItem(label: String, value: String) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			value,
			fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
			color = MaterialTheme.colorScheme.primary
		)
		Text(
			label,
			fontSize = 12.sp,
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)
	}
}
