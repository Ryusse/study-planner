package com.example.studyplanner.presentation.screens.focus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyplanner.presentation.state.FocusUiState

@Composable
fun PomodoroTimerUI(
	state: FocusUiState,
	onPause: () -> Unit,
	onResume: () -> Unit,
	onAbandon: () -> Unit,
	paddingValues: PaddingValues
) {
	val timeFormatted = formatTime(state.timerMs)
	val phaseLabel = if (state.isWorkPhase) "Trabaja" else "Descansa"
	val phaseColor = if (state.isWorkPhase) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues)
			.padding(16.dp),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			"${state.currentCycle}/${state.totalCycles} ciclos",
			fontSize = 18.sp,
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)

		Box(
			modifier = Modifier
				.size(240.dp)
				.background(phaseColor.copy(alpha = 0.1f), shape = CircleShape),
			contentAlignment = Alignment.Center
		) {
			Column(horizontalAlignment = Alignment.CenterHorizontally) {
				Text(timeFormatted, fontSize = 48.sp, fontWeight = FontWeight.Bold)
				Text(phaseLabel, fontSize = 16.sp, color = phaseColor)
			}
		}

		Row(
			modifier = Modifier,
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			if (state.timerRunning) {
				IconButton(
					onClick = onPause,
					modifier = Modifier
						.size(56.dp)
						.background(MaterialTheme.colorScheme.primary, shape = CircleShape)
				) {
					Icon(Icons.Default.Pause, "Pausar", tint = MaterialTheme.colorScheme.onPrimary)
				}
			} else {
				IconButton(
					onClick = onResume,
					modifier = Modifier
						.size(56.dp)
						.background(MaterialTheme.colorScheme.primary, shape = CircleShape)
				) {
					Icon(Icons.Default.PlayArrow, "Reanudar", tint = MaterialTheme.colorScheme.onPrimary)
				}
			}

			IconButton(
				onClick = onAbandon,
				modifier = Modifier
					.size(56.dp)
					.background(MaterialTheme.colorScheme.error, shape = CircleShape)
			) {
				Icon(Icons.Default.Stop, "Detener", tint = MaterialTheme.colorScheme.onError)
			}
		}
	}
}

private fun formatTime(ms: Long): String {
	val totalSeconds = ms / 1000
	val minutes = totalSeconds / 60
	val seconds = totalSeconds % 60
	return "%02d:%02d".format(minutes, seconds)
}
