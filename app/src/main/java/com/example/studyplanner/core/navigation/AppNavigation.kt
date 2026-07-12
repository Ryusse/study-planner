package com.example.studyplanner.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studyplanner.presentation.screens.dashboard.DashboardScreen
import com.example.studyplanner.presentation.screens.focus.FocusScreen
import com.example.studyplanner.presentation.screens.professor.ProfessorListScreen
import com.example.studyplanner.presentation.screens.subject.SubjectListScreen
import com.example.studyplanner.presentation.screens.task.TaskListScreen
import com.example.studyplanner.presentation.viewmodel.DashboardViewModel
import com.example.studyplanner.presentation.viewmodel.FocusViewModel
import com.example.studyplanner.presentation.viewmodel.ProfessorViewModel
import com.example.studyplanner.presentation.viewmodel.SubjectViewModel
import com.example.studyplanner.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
	professorViewModel: ProfessorViewModel,
	taskViewModel: TaskViewModel,
	focusViewModel: FocusViewModel,
	subjectViewModel: SubjectViewModel,
	dashboardViewModel: DashboardViewModel
) {
	val navController = rememberNavController()
	val navigationItems = NavItem.items

	val currentRoute = navController.currentBackStackEntryAsState()
		.value?.destination?.route
	val professorState by professorViewModel.uiState.collectAsState()

	val appBarTitle = when {
		currentRoute == NavRoutes.PROFESSOR_LIST -> when {
			professorState.showAddDialog && professorState.selectedProfessor != null -> "Editar Profesor"
			professorState.showAddDialog -> "Agregar Profesor"
			else -> "Profesores"
		}
		currentRoute == NavRoutes.DASHBOARD -> "Resumen"
		currentRoute == NavRoutes.SUBJECT_LIST -> "Materias"
		currentRoute == NavRoutes.TASK_LIST -> "Tareas"
		currentRoute == NavRoutes.FOCUS -> "Enfoque"
		currentRoute == NavRoutes.FOCUS_WITH_TASK -> "Enfoque"
		else -> "StudyPlanner"
	}

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						appBarTitle,
						maxLines = 1,
					)
				},
			)
		},
		bottomBar = {
			NavigationBar {
				val bottomBarRoute = navController.currentBackStackEntryAsState()
					.value?.destination?.route
				navigationItems.forEach { item ->
					NavigationBarItem(
						selected = bottomBarRoute == item.route,
						onClick = {
							navController.navigate(item.route) {
								popUpTo(NavRoutes.DASHBOARD) { saveState = true }
								launchSingleTop = true
								restoreState = true
							}
						},
						icon = {
							Icon(item.icon, null)
						},
						label = { Text(item.title) }
					)
				}
			}
		}
	) { paddingValues ->
		NavHost(
			navController = navController,
			startDestination = NavRoutes.DASHBOARD,
			modifier = Modifier.padding(paddingValues)
		) {
			composable(NavRoutes.DASHBOARD) {
				DashboardScreen(navController, dashboardViewModel)
			}
			composable(NavRoutes.PROFESSOR_LIST) {
				ProfessorListScreen(navController, professorViewModel)
			}
			composable(NavRoutes.SUBJECT_LIST) {
				SubjectListScreen(
					navController = navController,
					viewModel = subjectViewModel,
					professorViewModel = professorViewModel,
					taskViewModel = taskViewModel
				)
			}
			composable(NavRoutes.TASK_LIST) {
				TaskListScreen(navController, taskViewModel, subjectViewModel)
			}
			composable(NavRoutes.FOCUS) {
				FocusScreen(navController, focusViewModel)
			}
			composable(
				route = NavRoutes.FOCUS_WITH_TASK,
				arguments = listOf(navArgument("taskId") { type = NavType.IntType })
			) { backStackEntry ->
				val taskId = backStackEntry.arguments?.getInt("taskId")
				LaunchedEffect(taskId) {
					if (taskId != null) focusViewModel.startSession(taskId)
				}
				FocusScreen(navController, focusViewModel)
			}
		}
	}
}
