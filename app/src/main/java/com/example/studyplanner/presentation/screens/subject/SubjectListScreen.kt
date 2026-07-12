package com.example.studyplanner.presentation.screens.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.presentation.components.DeleteConfirmDialog
import com.example.studyplanner.presentation.components.LoadingDialog
import com.example.studyplanner.presentation.screens.subject.components.SubjectCard
import com.example.studyplanner.presentation.state.SubjectUiEvent
import com.example.studyplanner.presentation.viewmodel.ProfessorViewModel
import com.example.studyplanner.presentation.viewmodel.SubjectViewModel
import com.example.studyplanner.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectListScreen(
	navController: NavHostController,
	viewModel: SubjectViewModel,
	professorViewModel: ProfessorViewModel,
	taskViewModel: TaskViewModel,
	modifier: Modifier = Modifier
) {
	val state by viewModel.uiState.collectAsState()
	val professorState by professorViewModel.uiState.collectAsState()
	val taskState by taskViewModel.uiState.collectAsState()
	val snackbarHostState = remember { SnackbarHostState() }
	
	// skipPartiallyExpanded = true asegura que abra completo (height fit)
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	var subjectToDelete by remember { mutableStateOf<Subject?>(null) }

	LaunchedEffect(Unit) {
		viewModel.event.collect { event ->
			when (event) {
				is SubjectUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
			}
		}
	}

	fun professorNameFor(professorId: Int): String {
		return professorState.professors.find { it.id == professorId }?.name ?: "Sin profesor"
	}

	Box(modifier = modifier.fillMaxSize()) {
		if (state.subjects.isEmpty()) {
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				Text("No hay materias registradas")
			}
		} else {
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = androidx.compose.foundation.layout.PaddingValues(
					start = 16.dp,
					top = 8.dp,
					end = 16.dp,
					bottom = 88.dp
				),
				verticalArrangement = Arrangement.spacedBy(8.dp)
			) {
				items(state.subjects, key = { it.id }) { subject ->
					SubjectCard(
						subject = subject,
						professorName = professorNameFor(subject.professorId),
						pendingTaskCount = taskState.tasks.count { it.subjectId == subject.id && !it.isCompleted },
						onEdit = {
							viewModel.selectSubject(subject)
							viewModel.showAddDialog()
						},
						onDelete = { subjectToDelete = subject }
					)
				}
			}
		}

		ExtendedFloatingActionButton(
			onClick = {
				viewModel.clearSelection()
				viewModel.showAddDialog()
			},
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(16.dp),
			icon = { Icon(Icons.Default.Add, null) },
			text = { Text("Agregar materia") }
		)

		SnackbarHost(
			modifier = Modifier.align(Alignment.BottomCenter),
			hostState = snackbarHostState
		)
	}

	if (state.showAddDialog) {
		AddSubjectDialog(
			professors = professorState.professors,
			editingSubject = state.selectedSubject,
			onDismiss = { viewModel.hideAddDialog() },
			onSave = { subject ->
				if (state.selectedSubject != null) viewModel.updateSubject(subject) else viewModel.addSubject(subject)
			},
			sheetState = sheetState
		)
	}

	if (subjectToDelete != null) {
		DeleteConfirmDialog(
			isVisible = true,
			title = "Eliminar materia",
			text = "¿Estás seguro de que deseas eliminar ${subjectToDelete?.name}?",
			onConfirm = {
				viewModel.deleteSubject(subjectToDelete!!)
				subjectToDelete = null
			},
			onDismiss = { subjectToDelete = null }
		)
	}

	LoadingDialog(
		isVisible = state.loadingMessage != null,
		message = state.loadingMessage ?: ""
	)
}
