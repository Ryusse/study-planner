package com.example.studyplanner.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
	isVisible: Boolean,
	message: String = "Guardando información"
) {
	if (!isVisible) return

	Dialog(
		onDismissRequest = {},
		properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
	) {
		Column(
			modifier = Modifier
				.background(MaterialTheme.colorScheme.surface)
				.padding(40.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			CircularProgressIndicator()
			Text(message, color = MaterialTheme.colorScheme.onSurface)
		}
	}
}
