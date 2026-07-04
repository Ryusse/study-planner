package com.example.studyplanner.presentation.screens.professor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studyplanner.domain.model.Professor

@Composable
fun ProfessorsListContent(
	professors: List<Professor>,
	paddingValues: androidx.compose.foundation.layout.PaddingValues,
	onEdit: (Professor) -> Unit,
	onDeleteClick: (Professor) -> Unit
) {
	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues)
			.padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		items(professors) { professor ->
			ProfessorCard(
				professor = professor,
				onEdit = { onEdit(professor) },
				onDelete = { onDeleteClick(professor) }
			)
		}
	}
}
