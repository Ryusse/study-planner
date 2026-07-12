package com.example.studyplanner.presentation.screens.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.presentation.state.TaskStatusFilter

@Composable
fun TaskFilterBar(
	subjects: List<Subject>,
	filterSubjectId: Int?,
	filterPriority: String?,
	filterStatus: TaskStatusFilter,
	onSubjectSelected: (Int?) -> Unit,
	onPrioritySelected: (String?) -> Unit,
	onStatusSelected: (TaskStatusFilter) -> Unit,
	modifier: Modifier = Modifier
) {
	LazyRow(
		modifier = modifier.padding(vertical = 8.dp),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
	) {
		item {
			FilterChip(
				selected = filterStatus == TaskStatusFilter.PENDING,
				onClick = {
					onStatusSelected(
						if (filterStatus == TaskStatusFilter.PENDING) TaskStatusFilter.ALL else TaskStatusFilter.PENDING
					)
				},
				label = { Text("Pendientes") }
			)
		}
		item {
			FilterChip(
				selected = filterStatus == TaskStatusFilter.COMPLETED,
				onClick = {
					onStatusSelected(
						if (filterStatus == TaskStatusFilter.COMPLETED) TaskStatusFilter.ALL else TaskStatusFilter.COMPLETED
					)
				},
				label = { Text("Completadas") }
			)
		}
		items(listOf("Alta", "Media", "Baja")) { priority ->
			FilterChip(
				selected = filterPriority == priority,
				onClick = { onPrioritySelected(if (filterPriority == priority) null else priority) },
				label = { Text(priority) }
			)
		}
		items(subjects) { subject ->
			FilterChip(
				selected = filterSubjectId == subject.id,
				onClick = { onSubjectSelected(if (filterSubjectId == subject.id) null else subject.id) },
				label = { Text(subject.code) }
			)
		}
	}
}
