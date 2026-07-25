package com.example.studyplanner.presentation.screens.professor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.presentation.components.DeleteConfirmDialog
import com.example.studyplanner.presentation.components.LoadingDialog
import com.example.studyplanner.presentation.screens.professor.components.EmptyProfessorsList
import com.example.studyplanner.presentation.screens.professor.components.ProfessorsListContent
import com.example.studyplanner.presentation.state.ProfessorUiEvent
import com.example.studyplanner.presentation.viewmodel.ProfessorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessorListScreen(
	navController: NavHostController,
	viewModel: ProfessorViewModel,
	modifier: Modifier = Modifier
) {
	val state by viewModel.uiState.collectAsState()
	val snackbarHostState = remember { SnackbarHostState() }
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	var professorToDelete by remember { mutableStateOf<Professor?>(null) }
	
	LaunchedEffect(Unit) {
		viewModel.event.collect { event ->
			when (event) {
				is ProfessorUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
			}
		}
	}
	
	Box(modifier = modifier.fillMaxSize()) {
		if (state.professors.isEmpty()) {
			EmptyProfessorsList(androidx.compose.foundation.layout.PaddingValues(0.dp))
		} else {
			ProfessorsListContent(
				professors = state.professors,
				paddingValues = androidx.compose.foundation.layout.PaddingValues(0.dp),
				onEdit = { professor ->
					viewModel.selectProfessor(professor)
					viewModel.showAddDialog()
				},
				onDeleteClick = { professor -> professorToDelete = professor }
			)
		}
		
		ExtendedFloatingActionButton(
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(16.dp),
			onClick = {
				viewModel.clearSelection()
				viewModel.showAddDialog()
			},
			icon = { Icon(Icons.Filled.Edit, "Agregar docente") },
			text = { Text(text = "Agregar docente") },
		)
		
		SnackbarHost(
			modifier = Modifier.align(Alignment.BottomCenter),
			hostState = snackbarHostState
		)
	}
	
	LoadingDialog(
		isVisible = state.loadingMessage != null,
		message = state.loadingMessage ?: ""
	)
	
	if (professorToDelete != null) {
		DeleteConfirmDialog(
			title = "Eliminar profesor",
			text = "¿Estás seguro de que deseas eliminar a ${professorToDelete?.name}?",
			onConfirm = {
				viewModel.deleteProfessor(professorToDelete!!)
				professorToDelete = null
			},
			onDismiss = { professorToDelete = null }
		)
	}
	
	if (state.showAddDialog) {
		AddProfessorDialog(
			editingProfessor = state.selectedProfessor,
			onDismiss = { viewModel.hideAddDialog() },
			onSave = { professor ->
				if (state.selectedProfessor != null) viewModel.updateProfessor(professor) else viewModel.addProfessor(professor)
			},
			sheetState = sheetState
		)
	}
}
