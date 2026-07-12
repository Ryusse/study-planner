package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.model.TaskStats

data class DashboardUiState(
	val stats: TaskStats = TaskStats(total = 0, completed = 0, pending = 0),
	val pendingBySubject: Map<Subject, List<Task>> = emptyMap()
)
