package com.example.studyplanner.core.navigation

object NavRoutes {
	const val DASHBOARD = "dashboard"
	const val PROFESSOR_LIST = "professor_list"
	const val SUBJECT_LIST = "subject_list"
	const val TASK_LIST = "task_list"
	const val FOCUS = "focus"
	const val FOCUS_WITH_TASK = "focus/{taskId}"
}

fun focusRoute(taskId: Int) = "focus/$taskId"
