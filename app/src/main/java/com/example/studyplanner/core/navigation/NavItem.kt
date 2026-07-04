package com.example.studyplanner.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(val route: String, val title: String, val icon: ImageVector) {
	data object Dashboard : NavItem(NavRoutes.DASHBOARD, "Inicio", Icons.Default.Home)
	data object ProfessorList : NavItem(NavRoutes.PROFESSOR_LIST, "Profesores", Icons.Default.Person)
	data object SubjectList : NavItem(NavRoutes.SUBJECT_LIST, "Materias", Icons.Default.School)
	data object TaskList : NavItem(NavRoutes.TASK_LIST, "Tareas", Icons.Default.Task)
	data object Focus : NavItem(NavRoutes.FOCUS, "Enfoque", Icons.Default.Timer)

	companion object {
		val items = listOf(Dashboard, ProfessorList, SubjectList, TaskList, Focus)
	}
}
