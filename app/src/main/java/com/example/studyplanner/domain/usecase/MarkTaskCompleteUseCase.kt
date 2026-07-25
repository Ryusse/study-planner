package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.TaskRepository

class MarkTaskCompleteUseCase(private val repository: TaskRepository) {
	suspend operator fun invoke(task: Task, completed: Boolean) {
		repository.updateTask(
			task.copy(
				isCompleted = completed,
				completedAt = if (completed) System.currentTimeMillis() else null
			)
		)
	}
}
