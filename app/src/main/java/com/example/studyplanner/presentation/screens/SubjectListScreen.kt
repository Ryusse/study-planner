package com.example.studyplanner.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.presentation.components.DeleteConfirmDialog
import com.example.studyplanner.presentation.components.LoadingDialog
import com.example.studyplanner.presentation.viewmodel.SubjectViewModel

@Composable
fun SubjectListScreen(
	navController: NavHostController,
	viewModel: SubjectViewModel,
	modifier: Modifier = Modifier
) {
	val state by viewModel.uiState.collectAsState()
	var selectedSubjectForDelete by remember { mutableStateOf<Subject?>(null) }

	Scaffold(
		modifier = modifier,
		floatingActionButton = {
			FloatingActionButton(
				onClick = { /* TODO: Navigate to add subject */ }
			) {
				Icon(Icons.Default.Add, "Agregar materia")
			}
		}
	) { paddingValues ->
		if (state.subjects.isEmpty()) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.padding(paddingValues),
				contentAlignment = Alignment.Center
			) {
				Text("No hay materias")
			}
		} else {
			LazyColumn(
				modifier = Modifier
					.fillMaxSize()
					.padding(paddingValues)
					.padding(16.dp),
				verticalArrangement = Arrangement.spacedBy(8.dp)
			) {
				items(state.subjects) { subject ->
					Card(modifier = Modifier.fillMaxWidth()) {
						Column(modifier = Modifier.padding(16.dp)) {
							Text(
								subject.name,
								fontSize = 16.sp,
								fontWeight = FontWeight.Bold
							)
							Text(
								subject.code,
								fontSize = 12.sp,
								color = androidx.compose.ui.graphics.Color.Gray
							)
							IconButton(
								onClick = { selectedSubjectForDelete = subject }
							) {
								Icon(Icons.Default.Delete, "Eliminar")
							}
						}
					}
				}
			}
		}
	}

	if (selectedSubjectForDelete != null) {
		DeleteConfirmDialog(
			isVisible = true,
			title = "Eliminar materia",
			text = "¿Estás seguro de que deseas eliminar ${selectedSubjectForDelete?.name}?",
			onConfirm = {
				viewModel.deleteSubject(selectedSubjectForDelete!!)
				selectedSubjectForDelete = null
			},
			onDismiss = { selectedSubjectForDelete = null }
		)
	}

	LoadingDialog(isVisible = state.isLoading)
}
