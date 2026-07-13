package com.example.studyplanner.presentation.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.studyplanner.core.navigation.focusRoute
import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.presentation.components.DeleteConfirmDialog
import com.example.studyplanner.presentation.components.LoadingDialog
import com.example.studyplanner.presentation.components.TaskCard
import com.example.studyplanner.presentation.screens.task.components.TaskFilterBar
import com.example.studyplanner.presentation.state.TaskUiEvent
import com.example.studyplanner.presentation.viewmodel.SubjectViewModel
import com.example.studyplanner.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
	navController: NavHostController,
	viewModel: TaskViewModel,
	subjectViewModel: SubjectViewModel,
	modifier: Modifier = Modifier
) {
	val state by viewModel.uiState.collectAsState()
	val subjectState by subjectViewModel.uiState.collectAsState()
	val snackbarHostState = remember { SnackbarHostState() }
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	var taskToDelete by remember { mutableStateOf<Task?>(null) }
	
	LaunchedEffect(Unit) {
		viewModel.event.collect { event ->
			when (event) {
				is TaskUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
			}
		}
	}
	
	fun subjectNameFor(subjectId: Int): String {
		val subject = subjectState.subjects.find { it.id == subjectId }
		return subject?.let { "${it.code} - ${it.name}" } ?: "Materia eliminada"
	}
	
	Box(modifier = modifier.fillMaxSize()) {
		Column(modifier = Modifier.fillMaxSize()) {
			TaskFilterBar(
				subjects = subjectState.subjects,
				filterSubjectId = state.filterSubjectId,
				filterPriority = state.filterPriority,
				filterStatus = state.filterStatus,
				onSubjectSelected = viewModel::setFilterSubject,
				onPrioritySelected = viewModel::setFilterPriority,
				onStatusSelected = viewModel::setFilterStatus
			)
			
			if (state.filteredTasks.isEmpty()) {
				Box(
					modifier = Modifier.fillMaxSize(),
					contentAlignment = Alignment.Center
				) {
					Text("No hay tareas")
				}
			} else {
				LazyColumn(
					modifier = Modifier.fillMaxSize(),
					contentPadding = androidx.compose.foundation.layout.PaddingValues(
						start = 16.dp,
						top = 8.dp,
						end = 16.dp,
						bottom = 88.dp // Espacio para el FAB
					),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					items(state.filteredTasks, key = { it.id }) { task ->
						TaskCard(
							task = task,
							subjectName = subjectNameFor(task.subjectId),
							onToggleComplete = { completed -> viewModel.markComplete(task, completed) },
							onEdit = {
								viewModel.selectTask(task)
								viewModel.showAddDialog()
							},
							onDelete = { taskToDelete = task },
							onStartFocus = { navController.navigate(focusRoute(task.id)) }
						)
					}
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
			text = { Text("Agregar tarea") }
		)
		
		SnackbarHost(
			modifier = Modifier.align(Alignment.BottomCenter),
			hostState = snackbarHostState
		)
	}
	
	if (state.showAddDialog) {
		AddTaskDialog(
			subjects = subjectState.subjects,
			editingTask = state.selectedTask,
			onDismiss = { viewModel.hideAddDialog() },
			onSave = { task ->
				if (state.selectedTask != null) viewModel.updateTask(task) else viewModel.addTask(task)
			},
			sheetState = sheetState
		)
	}
	
	if (taskToDelete != null) {
		DeleteConfirmDialog(
			title = "Eliminar tarea",
			text = "¿Estás seguro de que deseas eliminar \"${taskToDelete?.title}\"?",
			onConfirm = {
				viewModel.deleteTask(taskToDelete!!)
				taskToDelete = null
			},
			onDismiss = { taskToDelete = null }
		)
	}
	
	LoadingDialog(
		isVisible = state.loadingMessage != null,
		message = state.loadingMessage ?: ""
	)
}
