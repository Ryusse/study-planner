package com.example.studyplanner.presentation.screens.subject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.presentation.components.CardAction
import com.example.studyplanner.presentation.components.CardActionsMenu

@Composable
fun SubjectCard(
	subject: Subject,
	professorName: String,
	pendingTaskCount: Int,
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
			Box(
				modifier = Modifier
					.size(12.dp)
					.clip(CircleShape)
					.background(runCatching { Color(android.graphics.Color.parseColor(subject.color)) }.getOrDefault(MaterialTheme.colorScheme.primary))
			)

			Column(
				modifier = Modifier
					.weight(1f)
					.padding(start = 12.dp)
			) {
				Text(subject.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
				Text(subject.code, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
				Text(professorName, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
				Text(subject.schedule, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
			}

			Text(
				"$pendingTaskCount pend.",
				fontSize = 12.sp,
				fontWeight = FontWeight.SemiBold,
				color = MaterialTheme.colorScheme.primary
			)

			CardActionsMenu(
				actions = listOf(
					CardAction("Editar", Icons.Default.Edit, onClick = onEdit),
					CardAction("Eliminar", Icons.Default.Delete, tint = MaterialTheme.colorScheme.error, onClick = onDelete)
				)
			)
		}
	}
}
