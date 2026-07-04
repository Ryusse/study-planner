package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.TaskRepository

class DeleteTaskUseCase(private val repository: TaskRepository) {
	suspend operator fun invoke(task: Task) = repository.deleteTask(task)
}
