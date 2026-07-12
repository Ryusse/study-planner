package com.example.studyplanner.presentation.screens.task

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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.material3.rememberDatePickerState
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
import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val PRIORITIES = listOf("Alta", "Media", "Baja")
private val TYPES = listOf("Tarea", "Examen", "Proyecto")
private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("es"))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
	subjects: List<Subject>,
	editingTask: Task?,
	onDismiss: () -> Unit,
	onSave: (Task) -> Unit,
	sheetState: SheetState = rememberModalBottomSheetState()
) {
	val isEditing = editingTask != null
	val title = if (isEditing) "Editar Tarea" else "Agregar Tarea"

	var selectedSubject by remember(editingTask) {
		mutableStateOf(subjects.find { it.id == editingTask?.subjectId } ?: subjects.firstOrNull())
	}
	var taskTitle by remember(editingTask) { mutableStateOf(editingTask?.title ?: "") }
	var deadline by remember(editingTask) { mutableStateOf(editingTask?.deadline ?: System.currentTimeMillis()) }
	var priority by remember(editingTask) { mutableStateOf(editingTask?.priority ?: PRIORITIES[1]) }
	var type by remember(editingTask) { mutableStateOf(editingTask?.type ?: TYPES[0]) }
	var estimatedTime by remember(editingTask) { mutableStateOf(editingTask?.estimatedTime?.toString() ?: "") }

	var titleError by remember(editingTask) { mutableStateOf<String?>(null) }
	var estimatedTimeError by remember(editingTask) { mutableStateOf<String?>(null) }
	var subjectError by remember(editingTask) { mutableStateOf<String?>(null) }

	var subjectMenuExpanded by remember { mutableStateOf(false) }
	var priorityMenuExpanded by remember { mutableStateOf(false) }
	var typeMenuExpanded by remember { mutableStateOf(false) }
	var showDatePicker by remember { mutableStateOf(false) }

	val validateForm = {
		titleError = when {
			taskTitle.isBlank() -> "Título requerido"
			taskTitle.length > 150 -> "Máximo 150 caracteres"
			else -> null
		}
		subjectError = if (selectedSubject == null) "Selecciona una materia" else null
		estimatedTimeError = when {
			estimatedTime.isBlank() -> "Tiempo estimado requerido"
			estimatedTime.toIntOrNull() == null || estimatedTime.toInt() <= 0 -> "Debe ser un número mayor a 0"
			else -> null
		}
		titleError == null && subjectError == null && estimatedTimeError == null
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
				.padding(bottom = 16.dp)
				.imePadding()
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			Text(title, style = MaterialTheme.typography.headlineSmall)

			Column {
				ExposedDropdownMenuBox(
					expanded = subjectMenuExpanded,
					onExpandedChange = { subjectMenuExpanded = it }
				) {
					OutlinedTextField(
						value = selectedSubject?.let { "${it.code} - ${it.name}" } ?: "",
						onValueChange = {},
						readOnly = true,
						label = { Text("Materia") },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(subjectMenuExpanded) },
						isError = subjectError != null,
						modifier = Modifier
							.fillMaxWidth()
							.menuAnchor()
					)
					androidx.compose.material3.DropdownMenu(
						expanded = subjectMenuExpanded,
						onDismissRequest = { subjectMenuExpanded = false }
					) {
						subjects.forEach { subject ->
							DropdownMenuItem(
								text = { Text("${subject.code} - ${subject.name}") },
								onClick = {
									selectedSubject = subject
									subjectError = null
									subjectMenuExpanded = false
								}
							)
						}
					}
				}
				if (subjectError != null) {
					Text(subjectError!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
				}
			}

			Column {
				OutlinedTextField(
					value = taskTitle,
					onValueChange = { taskTitle = it; titleError = null },
					label = { Text("Título") },
					modifier = Modifier.fillMaxWidth(),
					isError = titleError != null
				)
				if (titleError != null) {
					Text(titleError!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
				}
			}

			OutlinedTextField(
				value = dateFormat.format(Date(deadline)),
				onValueChange = {},
				readOnly = true,
				label = { Text("Fecha límite") },
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 0.dp),
				trailingIcon = {
					OutlinedButton(onClick = { showDatePicker = true }) { Text("Elegir") }
				}
			)

			Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
				ExposedDropdownMenuBox(
					expanded = priorityMenuExpanded,
					onExpandedChange = { priorityMenuExpanded = it },
					modifier = Modifier.weight(1f)
				) {
					OutlinedTextField(
						value = priority,
						onValueChange = {},
						readOnly = true,
						label = { Text("Prioridad") },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(priorityMenuExpanded) },
						modifier = Modifier
							.fillMaxWidth()
							.menuAnchor()
					)
					androidx.compose.material3.DropdownMenu(
						expanded = priorityMenuExpanded,
						onDismissRequest = { priorityMenuExpanded = false }
					) {
						PRIORITIES.forEach { option ->
							DropdownMenuItem(
								text = { Text(option) },
								onClick = { priority = option; priorityMenuExpanded = false }
							)
						}
					}
				}

				ExposedDropdownMenuBox(
					expanded = typeMenuExpanded,
					onExpandedChange = { typeMenuExpanded = it },
					modifier = Modifier.weight(1f)
				) {
					OutlinedTextField(
						value = type,
						onValueChange = {},
						readOnly = true,
						label = { Text("Tipo") },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(typeMenuExpanded) },
						modifier = Modifier
							.fillMaxWidth()
							.menuAnchor()
					)
					androidx.compose.material3.DropdownMenu(
						expanded = typeMenuExpanded,
						onDismissRequest = { typeMenuExpanded = false }
					) {
						TYPES.forEach { option ->
							DropdownMenuItem(
								text = { Text(option) },
								onClick = { type = option; typeMenuExpanded = false }
							)
						}
					}
				}
			}

			Column {
				OutlinedTextField(
					value = estimatedTime,
					onValueChange = { estimatedTime = it.filter(Char::isDigit); estimatedTimeError = null },
					label = { Text("Tiempo estimado (min)") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					modifier = Modifier.fillMaxWidth(),
					isError = estimatedTimeError != null
				)
				if (estimatedTimeError != null) {
					Text(estimatedTimeError!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
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
							val task = (editingTask ?: Task(
								subjectId = selectedSubject!!.id,
								title = "",
								deadline = deadline,
								priority = priority,
								type = type,
								estimatedTime = 0
							)).copy(
								subjectId = selectedSubject!!.id,
								title = taskTitle,
								deadline = deadline,
								priority = priority,
								type = type,
								estimatedTime = estimatedTime.toInt()
							)
							onSave(task)
						}
					},
					modifier = Modifier.weight(1f)
				) {
					Text(if (isEditing) "Actualizar" else "Agregar")
				}
			}
		}
	}

	if (showDatePicker) {
		val datePickerState = rememberDatePickerState(initialSelectedDateMillis = deadline)
		DatePickerDialog(
			onDismissRequest = { showDatePicker = false },
			confirmButton = {
				Button(onClick = {
					datePickerState.selectedDateMillis?.let { deadline = it }
					showDatePicker = false
				}) { Text("Aceptar") }
			},
			dismissButton = {
				OutlinedButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
			}
		) {
			DatePicker(state = datePickerState)
		}
	}
}
