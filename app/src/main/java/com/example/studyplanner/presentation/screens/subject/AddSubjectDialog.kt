package com.example.studyplanner.presentation.screens.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.domain.model.Subject

private val SUBJECT_COLORS = listOf(
	"#EF5350", "#EC407A", "#AB47BC", "#5C6BC0",
	"#42A5F5", "#26A69A", "#66BB6A", "#FFA726"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubjectDialog(
	professors: List<Professor>,
	editingSubject: Subject?,
	onDismiss: () -> Unit,
	onSave: (Subject) -> Unit,
	sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
	val isEditing = editingSubject != null
	val title = if (isEditing) "Editar Materia" else "Agregar Materia"
	
	var code by remember(editingSubject) { mutableStateOf(editingSubject?.code ?: "") }
	var name by remember(editingSubject) { mutableStateOf(editingSubject?.name ?: "") }
	var schedule by remember(editingSubject) { mutableStateOf(editingSubject?.schedule ?: "") }
	var selectedColor by remember(editingSubject) {
		mutableStateOf(
			editingSubject?.color ?: SUBJECT_COLORS.first()
		)
	}
	var selectedProfessor by remember(editingSubject) {
		mutableStateOf(professors.find { it.id == editingSubject?.professorId }
			?: professors.firstOrNull())
	}
	
	var codeError by remember(editingSubject) { mutableStateOf<String?>(null) }
	var nameError by remember(editingSubject) { mutableStateOf<String?>(null) }
	var scheduleError by remember(editingSubject) { mutableStateOf<String?>(null) }
	var professorError by remember(editingSubject) { mutableStateOf<String?>(null) }
	var professorMenuExpanded by remember { mutableStateOf(false) }
	
	val validateForm = {
		codeError = if (code.isBlank()) "Código requerido" else null
		nameError = if (name.isBlank()) "Nombre requerido" else null
		scheduleError = if (schedule.isBlank()) "Horario requerido" else null
		professorError = if (selectedProfessor == null) "Selecciona un profesor" else null
		codeError == null && nameError == null && scheduleError == null && professorError == null
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
					value = code,
					onValueChange = { code = it; codeError = null },
					label = { Text("Código (ej: IS101)") },
					modifier = Modifier.fillMaxWidth(),
					singleLine = true,
					isError = codeError != null
				)
				if (codeError != null) {
					Text(
						codeError!!,
						color = Color.Red,
						fontSize = 12.sp,
						modifier = Modifier.padding(start = 16.dp, top = 4.dp)
					)
				}
			}
			
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
				ExposedDropdownMenuBox(
					expanded = professorMenuExpanded,
					onExpandedChange = { professorMenuExpanded = it }
				) {
					OutlinedTextField(
						value = selectedProfessor?.name ?: "",
						onValueChange = {},
						readOnly = true,
						label = { Text("Profesor") },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(professorMenuExpanded) },
						isError = professorError != null,
						modifier = Modifier
							.fillMaxWidth()
							.menuAnchor()
					)
					androidx.compose.material3.DropdownMenu(
						expanded = professorMenuExpanded,
						onDismissRequest = { professorMenuExpanded = false }
					) {
						professors.forEach { professor ->
							DropdownMenuItem(
								text = { Text(professor.name) },
								onClick = {
									selectedProfessor = professor
									professorError = null
									professorMenuExpanded = false
								}
							)
						}
					}
				}
				if (professorError != null) {
					Text(
						professorError!!,
						color = Color.Red,
						fontSize = 12.sp,
						modifier = Modifier.padding(start = 16.dp, top = 4.dp)
					)
				}
			}
			
			Column {
				OutlinedTextField(
					value = schedule,
					onValueChange = { schedule = it; scheduleError = null },
					label = { Text("Horario (ej: Lun-Mie 08:00-10:00)") },
					modifier = Modifier.fillMaxWidth(),
					singleLine = true,
					isError = scheduleError != null
				)
				if (scheduleError != null) {
					Text(
						scheduleError!!,
						color = Color.Red,
						fontSize = 12.sp,
						modifier = Modifier.padding(start = 16.dp, top = 4.dp)
					)
				}
			}
			
			Column {
				Text("Color", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
				Row(
					modifier = Modifier.padding(top = 8.dp),
					horizontalArrangement = Arrangement.spacedBy(8.dp)
				) {
					SUBJECT_COLORS.forEach { hex ->
						val color = Color(android.graphics.Color.parseColor(hex))
						Column(
							modifier = Modifier
								.size(32.dp)
								.clip(CircleShape)
								.background(color)
								.border(
									width = if (selectedColor == hex) 3.dp else 0.dp,
									color = MaterialTheme.colorScheme.onSurface,
									shape = CircleShape
								)
								.clickable { selectedColor = hex }
						) {}
					}
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
							val subject = (editingSubject ?: Subject(
								code = "",
								name = "",
								professorId = selectedProfessor!!.id,
								schedule = "",
								color = selectedColor
							)).copy(
								code = code,
								name = name,
								professorId = selectedProfessor!!.id,
								schedule = schedule,
								color = selectedColor
							)
							onSave(subject)
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
