package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.model.TaskPolicy
import com.example.studyplanner.domain.repository.TaskRepository

class UpdateTaskUseCase(private val repository: TaskRepository) {
	suspend operator fun invoke(task: Task) {
		require(task.title.isNotBlank()) { "Título requerido" }
		require(task.title.length <= TaskPolicy.TITLE_MAX_LENGTH) { "Máximo ${TaskPolicy.TITLE_MAX_LENGTH} caracteres" }
		require(task.estimatedTime > 0) { "El tiempo estimado debe ser mayor a 0" }
		val startOfToday = System.currentTimeMillis().let { it - it % 86_400_000L }
		require(task.deadline >= startOfToday) { "La fecha límite no puede ser anterior a hoy" }
		repository.updateTask(task)
	}
}
