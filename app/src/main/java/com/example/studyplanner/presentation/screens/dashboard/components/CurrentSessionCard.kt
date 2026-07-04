package com.example.studyplanner.presentation.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studyplanner.core.navigation.NavRoutes
import com.example.studyplanner.domain.model.FocusSession

@Composable
fun CurrentSessionCard(
	session: FocusSession?,
	navController: NavHostController
) {
	if (session == null) return

	Card(modifier = Modifier.fillMaxWidth()) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Text("Sesión en progreso", fontSize = 14.sp, color = Color.Gray)
			Text("Tarea #${session.taskId}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
			Button(
				onClick = { navController.navigate(NavRoutes.FOCUS) },
				modifier = Modifier.align(Alignment.End)
			) {
				Text("Continuar")
			}
		}
	}
}
