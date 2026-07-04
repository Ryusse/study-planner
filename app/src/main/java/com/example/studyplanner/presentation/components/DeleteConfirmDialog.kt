package com.example.studyplanner.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmDialog(
	isVisible: Boolean,
	title: String,
	text: String,
	onConfirm: () -> Unit,
	onDismiss: () -> Unit
) {
	if (!isVisible) return

	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text(title) },
		text = { Text(text) },
		confirmButton = {
			Button(onClick = onConfirm) {
				Text("Eliminar")
			}
		},
		dismissButton = {
			Button(onClick = onDismiss) {
				Text("Cancelar")
			}
		}
	)
}
