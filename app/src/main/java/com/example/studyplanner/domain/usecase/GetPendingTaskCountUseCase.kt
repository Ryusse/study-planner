package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPendingTaskCountUseCase(private val repository: TaskRepository) {
	operator fun invoke(): Flow<Int> = repository.getAllTasks().map { tasks ->
		tasks.count { !it.isCompleted }
	}
}
