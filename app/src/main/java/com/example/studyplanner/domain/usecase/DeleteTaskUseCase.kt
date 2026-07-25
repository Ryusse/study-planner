package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.FocusSessionRepository
import com.example.studyplanner.domain.repository.TaskRepository

class DeleteTaskUseCase(
	private val repository: TaskRepository,
	private val focusSessionRepository: FocusSessionRepository
) {
	suspend operator fun invoke(task: Task) {
		require(focusSessionRepository.countActiveByTask(task.id) == 0) {
			"No se puede eliminar: la tarea tiene una sesión de estudio activa"
		}
		repository.deleteTask(task)
	}
}
