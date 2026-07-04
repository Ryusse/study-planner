package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetUrgentTasksUseCase(private val repository: TaskRepository) {
	operator fun invoke(): Flow<List<Task>> = repository.getThreeUrgentTasks()
}
