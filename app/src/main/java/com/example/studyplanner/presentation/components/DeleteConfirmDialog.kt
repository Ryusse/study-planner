package com.example.studyplanner.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmDialog(
	title: String,
	text: String,
	onConfirm: () -> Unit,
	onDismiss: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text(title) },
		text = { Text(text) },
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
