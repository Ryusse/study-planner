package com.example.studyplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.studyplanner.core.navigation.AppNavigation
import com.example.studyplanner.core.ui.theme.StudyPlannerTheme
import com.example.studyplanner.di.AppModule

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		
		super.onCreate(savedInstanceState)
		
		val professorViewModel = AppModule.provideProfessorViewModel(applicationContext)
		val taskViewModel = AppModule.provideTaskViewModel(applicationContext)
		val focusViewModel = AppModule.provideFocusViewModel(applicationContext)
		val subjectViewModel = AppModule.provideSubjectViewModel(applicationContext)
		val dashboardViewModel = AppModule.provideDashboardViewModel(applicationContext)

		setContent {
			StudyPlannerTheme(darkTheme = true, dynamicColor = false) {
				AppNavigation(
					professorViewModel = professorViewModel,
					taskViewModel = taskViewModel,
					focusViewModel = focusViewModel,
					subjectViewModel = subjectViewModel,
					dashboardViewModel = dashboardViewModel
				)
			}
		}
	}
}
