package com.example.studyplanner.presentation.screens.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
			val selected = filterStatus == TaskStatusFilter.PENDING
			FilterChip(
				selected = selected,
				onClick = {
					onStatusSelected(
						if (filterStatus == TaskStatusFilter.PENDING) TaskStatusFilter.ALL else TaskStatusFilter.PENDING
					)
				},
				label = { Text("Pendientes") },
				leadingIcon = {
					Icon(
						imageVector = if (selected) Icons.Default.Done else Icons.Default.Schedule,
						contentDescription = null,
						modifier = Modifier.size(FilterChipDefaults.IconSize)
					)
				}
			)
		}
		item {
			val selected = filterStatus == TaskStatusFilter.COMPLETED
			FilterChip(
				selected = selected,
				onClick = {
					onStatusSelected(
						if (filterStatus == TaskStatusFilter.COMPLETED) TaskStatusFilter.ALL else TaskStatusFilter.COMPLETED
					)
				},
				label = { Text("Completadas") },
				leadingIcon = {
					Icon(
						imageVector = if (selected) Icons.Default.Done else Icons.Default.CheckCircle,
						contentDescription = null,
						modifier = Modifier.size(FilterChipDefaults.IconSize)
					)
				}
			)
		}
		items(listOf("Alta", "Media", "Baja")) { priority ->
			val selected = filterPriority == priority
			FilterChip(
				selected = selected,
				onClick = { onPrioritySelected(if (filterPriority == priority) null else priority) },
				label = { Text(priority) },
				leadingIcon = {
					Icon(
						imageVector = if (selected) Icons.Default.Done else Icons.Default.Flag,
						contentDescription = null,
						modifier = Modifier.size(FilterChipDefaults.IconSize)
					)
				}
			)
		}
		items(subjects) { subject ->
			val selected = filterSubjectId == subject.id
			FilterChip(
				selected = selected,
				onClick = { onSubjectSelected(if (filterSubjectId == subject.id) null else subject.id) },
				label = { Text(subject.code) },
				leadingIcon = {
					Icon(
						imageVector = if (selected) Icons.Default.Done else Icons.Default.Book,
						contentDescription = null,
						modifier = Modifier.size(FilterChipDefaults.IconSize)
					)
				}
			)
		}
	}
}
