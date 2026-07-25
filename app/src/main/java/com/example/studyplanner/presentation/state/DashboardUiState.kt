package com.example.studyplanner.presentation.state

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.model.SubjectProgress
import com.example.studyplanner.domain.model.TaskStats

data class DashboardUiState(
	val stats: TaskStats = TaskStats(total = 0, completed = 0, pending = 0),
	val pendingBySubject: Map<Subject, SubjectProgress> = emptyMap(),
	val streak: Int = 0,
	val points: Int = 0
)
