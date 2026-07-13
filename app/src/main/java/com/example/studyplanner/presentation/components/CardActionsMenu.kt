package com.example.studyplanner.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class CardAction(
	val label: String,
	val icon: ImageVector,
	val tint: Color? = null,
	val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardActionsMenu(actions: List<CardAction>) {
	var showSheet by remember { mutableStateOf(false) }
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

	IconButton(onClick = { showSheet = true }) {
		Icon(Icons.Default.MoreVert, "Más opciones")
	}

	if (showSheet) {
		ModalBottomSheet(
			onDismissRequest = { showSheet = false },
			sheetState = sheetState,
			scrimColor = Color.Black.copy(alpha = 0.32f)
		) {
			Column(modifier = Modifier.padding(bottom = 32.dp)) {
				actions.forEach { action ->
					ListItem(
						headlineContent = { Text(action.label, color = action.tint ?: Color.Unspecified) },
						leadingContent = { Icon(action.icon, null, tint = action.tint ?: Color.Unspecified) },
						modifier = Modifier
							.fillMaxWidth()
							.clickable {
								showSheet = false
								action.onClick()
							}
					)
				}
			}
		}
	}
}
