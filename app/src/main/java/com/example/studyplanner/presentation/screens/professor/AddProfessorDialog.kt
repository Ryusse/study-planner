package com.example.studyplanner.presentation.screens.professor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.presentation.viewmodel.ProfessorViewModel

private fun isValidEmail(email: String): Boolean {
	return email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProfessorDialog(
	viewModel: ProfessorViewModel,
	onDismiss: () -> Unit,
	sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
	val state by viewModel.uiState.collectAsState()
	var name by remember(state.selectedProfessor) {
		mutableStateOf(
			state.selectedProfessor?.name ?: ""
		)
	}
	var email by remember(state.selectedProfessor) {
		mutableStateOf(
			state.selectedProfessor?.email ?: ""
		)
	}
	var department by remember(state.selectedProfessor) {
		mutableStateOf(
			state.selectedProfessor?.department ?: ""
		)
	}
	var nameError by remember(state.selectedProfessor) { mutableStateOf<String?>(null) }
	var emailError by remember(state.selectedProfessor) { mutableStateOf<String?>(null) }
	var departmentError by remember(state.selectedProfessor) { mutableStateOf<String?>(null) }
	
	val isEditing = state.selectedProfessor != null
	val title = if (isEditing) "Editar Profesor" else "Agregar Profesor"
	
	val validateForm = {
		nameError = when {
			name.isBlank() -> "Nombre requerido"
			name.length < 3 -> "Mínimo 3 caracteres"
			name.length > 100 -> "Máximo 100 caracteres"
			else -> null
		}
		emailError = when {
			email.isBlank() -> "Email requerido"
			!isValidEmail(email) -> "Email inválido"
			else -> null
		}
		departmentError = when {
			department.isBlank() -> "Departamento requerido"
			department.length < 3 -> "Mínimo 3 caracteres"
			department.length > 100 -> "Máximo 100 caracteres"
			else -> null
		}
		nameError == null && emailError == null && departmentError == null
	}
	
	ModalBottomSheet(
		onDismissRequest = {
			viewModel.clearSelection()
			onDismiss()
		},
		sheetState = sheetState,
		scrimColor = Color.Black.copy(alpha = 0.32f)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
				.padding(bottom = 32.dp)
				.imePadding()
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			Text(title, style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
			
			Column {
				OutlinedTextField(
					value = name,
					onValueChange = { name = it; nameError = null },
					label = { Text("Nombre") },
					modifier = Modifier.fillMaxWidth(),
					singleLine = true,
					isError = nameError != null
				)
				if (nameError != null) {
					Text(
						nameError!!,
						color = Color.Red,
						fontSize = 12.sp,
						modifier = Modifier.padding(start = 16.dp, top = 4.dp)
					)
				}
			}
			
			Column {
				OutlinedTextField(
					value = email,
					onValueChange = { email = it; emailError = null },
					label = { Text("Email") },
					modifier = Modifier.fillMaxWidth(),
					singleLine = true,
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
					isError = emailError != null
				)
				if (emailError != null) {
					Text(
						emailError!!,
						color = Color.Red,
						fontSize = 12.sp,
						modifier = Modifier.padding(start = 16.dp, top = 4.dp)
					)
				}
			}
			
			Column {
				OutlinedTextField(
					value = department,
					onValueChange = { department = it; departmentError = null },
					label = { Text("Departamento") },
					modifier = Modifier.fillMaxWidth(),
					singleLine = true,
					isError = departmentError != null
				)
				if (departmentError != null) {
					Text(
						departmentError!!,
						color = Color.Red,
						fontSize = 12.sp,
						modifier = Modifier.padding(start = 16.dp, top = 4.dp)
					)
				}
			}
			
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 16.dp),
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				OutlinedButton(
					onClick = {
						viewModel.clearSelection()
						onDismiss()
					},
					modifier = Modifier.weight(1f),
					enabled = state.loadingMessage == null
				) {
					Text("Cancelar")
				}
				
				Button(
					onClick = {
						if (validateForm()) {
							val professor = if (isEditing) {
								state.selectedProfessor!!.copy(
									name = name,
									email = email,
									department = department
								)
							} else {
								Professor(
									name = name,
									email = email,
									department = department
								)
							}
							
							if (isEditing) {
								viewModel.updateProfessor(professor)
							} else {
								viewModel.addProfessor(professor)
							}
						}
					},
					modifier = Modifier.weight(1f),
					enabled = state.loadingMessage == null
				) {
					Text(if (isEditing) "Actualizar" else "Agregar")
				}
			}
		}
	}
}
