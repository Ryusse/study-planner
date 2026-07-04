package com.example.studyplanner.presentation.screens.focus.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ValidationDialog(
	onYes: () -> Unit,
	onNo: () -> Unit
) {
	AlertDialog(
		onDismissRequest = { },
		title = { Text("¿Completaste los 4 ciclos Pomodoro?") },
		text = { Text("Confirma si realmente trabajaste en la sesión completa.") },
		confirmButton = {
			Button(onClick = onYes) {
				Text("Sí")
			}
		},
		dismissButton = {
			Button(onClick = onNo) {
				Text("No")
			}
		}
	)
}
