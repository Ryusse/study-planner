package com.example.studyplanner.presentation.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PendingTasksCard(pendingCount: Int) {
	Card(modifier = Modifier.fillMaxWidth()) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			Text("Tareas Pendientes", fontSize = 14.sp, color = Color.Gray)
			Text("$pendingCount tareas", fontSize = 24.sp, fontWeight = FontWeight.Bold)
		}
	}
}
