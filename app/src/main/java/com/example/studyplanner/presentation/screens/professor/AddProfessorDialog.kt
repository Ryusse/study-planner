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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import com.example.studyplanner.domain.model.ProfessorPolicy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProfessorDialog(
	editingProfessor: Professor?,
	onDismiss: () -> Unit,
	onSave: (Professor) -> Unit,
	sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
	var name by remember(editingProfessor) { mutableStateOf(editingProfessor?.name ?: "") }
	var email by remember(editingProfessor) { mutableStateOf(editingProfessor?.email ?: "") }
	var department by remember(editingProfessor) { mutableStateOf(editingProfessor?.department ?: "") }
	var nameError by remember(editingProfessor) { mutableStateOf<String?>(null) }
	var emailError by remember(editingProfessor) { mutableStateOf<String?>(null) }
	var departmentError by remember(editingProfessor) { mutableStateOf<String?>(null) }

	val isEditing = editingProfessor != null
	val title = if (isEditing) "Editar Profesor" else "Agregar Profesor"

	val validateForm = {
		nameError = when {
			name.isBlank() -> "Nombre requerido"
			name.length < ProfessorPolicy.NAME_MIN_LENGTH -> "Mínimo ${ProfessorPolicy.NAME_MIN_LENGTH} caracteres"
			name.length > ProfessorPolicy.NAME_MAX_LENGTH -> "Máximo ${ProfessorPolicy.NAME_MAX_LENGTH} caracteres"
			else -> null
		}
		emailError = when {
			email.isBlank() -> "Email requerido"
			!ProfessorPolicy.isValidEmail(email) -> "Email inválido"
			else -> null
		}
		departmentError = when {
			department.isBlank() -> "Departamento requerido"
			department.length < ProfessorPolicy.NAME_MIN_LENGTH -> "Mínimo ${ProfessorPolicy.NAME_MIN_LENGTH} caracteres"
			department.length > ProfessorPolicy.NAME_MAX_LENGTH -> "Máximo ${ProfessorPolicy.NAME_MAX_LENGTH} caracteres"
			else -> null
		}
		nameError == null && emailError == null && departmentError == null
	}

	ModalBottomSheet(
		onDismissRequest = onDismiss,
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
			Text(title, style = MaterialTheme.typography.headlineSmall)

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
				OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
					Text("Cancelar")
				}
				Button(
					onClick = {
						if (validateForm()) {
							val professor = (editingProfessor ?: Professor(
								name = "",
								email = "",
								department = ""
							)).copy(
								name = name,
								email = email,
								department = department
							)
							onSave(professor)
						}
					},
					modifier = Modifier.weight(1f)
				) {
					Text(if (isEditing) "Actualizar" else "Agregar")
				}
			}
		}
	}
}
