package com.example.studyplanner.presentation.screens.professor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyProfessorsList(paddingValues: androidx.compose.foundation.layout.PaddingValues) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues),
		contentAlignment = Alignment.Center
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			Text(
				"No hay profesores registrados",
				fontSize = 18.sp,
				fontWeight = FontWeight.Bold
			)
			Text(
				"Presiona el botón + para agregar uno",
				fontSize = 14.sp,
				color = Color.Gray
			)
		}
	}
}
