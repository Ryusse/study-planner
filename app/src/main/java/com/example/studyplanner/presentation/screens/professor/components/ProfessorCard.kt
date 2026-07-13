package com.example.studyplanner.presentation.screens.professor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.presentation.components.CardAction
import com.example.studyplanner.presentation.components.CardActionsMenu

@Composable
fun ProfessorCard(
	professor: Professor,
	onEdit: () -> Unit,
	onDelete: () -> Unit
) {
	Card(modifier = Modifier.fillMaxWidth()) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Column(modifier = Modifier.weight(1f)) {
				Text(
					professor.name,
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold
				)
				Text(
					professor.email,
					fontSize = 12.sp,
					color = Color.Gray
				)
				Text(
					professor.department,
					fontSize = 12.sp,
					color = Color.Gray
				)
			}

			CardActionsMenu(
				actions = listOf(
					CardAction("Editar", Icons.Default.Edit, onClick = onEdit),
					CardAction("Eliminar", Icons.Default.Delete, tint = MaterialTheme.colorScheme.error, onClick = onDelete)
				)
			)
		}
	}
}
