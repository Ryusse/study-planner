package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.TaskStats
import com.example.studyplanner.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTaskStatsUseCase(private val repository: TaskRepository) {
	operator fun invoke(): Flow<TaskStats> = repository.getAllTasks().map { tasks ->
		val completed = tasks.count { it.isCompleted }
		TaskStats(
			total = tasks.size,
			completed = completed,
			pending = tasks.size - completed
		)
	}
}
