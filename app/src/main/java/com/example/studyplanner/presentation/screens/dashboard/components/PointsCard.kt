package com.example.studyplanner.presentation.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PointsCard(points: Int) {
	Card(modifier = Modifier.fillMaxWidth()) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.background(MaterialTheme.colorScheme.surfaceContainerLow)
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			Text("Puntos Semana", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
			Text("$points pts", fontSize = 24.sp, fontWeight = FontWeight.Bold)
		}
	}
}
