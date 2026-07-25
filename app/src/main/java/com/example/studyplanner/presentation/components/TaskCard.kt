package com.example.studyplanner.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyplanner.domain.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("es"))

@Composable
fun TaskCard(
	task: Task,
	subjectName: String,
	modifier: Modifier = Modifier,
	onToggleComplete: (Boolean) -> Unit = {},
	onEdit: () -> Unit = {},
	onDelete: () -> Unit = {},
	onStartFocus: () -> Unit = {}
) {
	Card(modifier = modifier.fillMaxWidth()) {
		Column(modifier = Modifier.padding(16.dp)) {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Checkbox(
					checked = task.isCompleted,
					onCheckedChange = onToggleComplete
				)
				Column(modifier = Modifier.weight(1f)) {
					Text(
						task.title,
						fontSize = 16.sp,
						fontWeight = FontWeight.Bold,
						textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
					)
					Text(
						subjectName,
						fontSize = 12.sp,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}

			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 8.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Column {
					Text("Vence: ${dateFormat.format(Date(task.deadline))}", fontSize = 12.sp)
					Text("${task.priority} · ${task.type}", fontSize = 12.sp)
				}

				CardActionsMenu(
					actions = listOfNotNull(
						if (!task.isCompleted) CardAction("Iniciar enfoque", Icons.Default.Timer, onClick = onStartFocus) else null,
						CardAction("Editar", Icons.Default.Edit, onClick = onEdit),
						CardAction("Eliminar", Icons.Default.Delete, onClick = onDelete)
					)
				)
			}
		}
	}
}
