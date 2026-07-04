package com.example.studyplanner.presentation.screens.professor.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmationDialog(
	professorName: String,
	onConfirm: () -> Unit,
	onDismiss: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Eliminar profesor") },
		text = { Text("¿Estás seguro de que deseas eliminar a $professorName?") },
		confirmButton = {
			TextButton(onClick = onConfirm) {
				Text("Eliminar")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Cancelar")
			}
		}
	)
}
