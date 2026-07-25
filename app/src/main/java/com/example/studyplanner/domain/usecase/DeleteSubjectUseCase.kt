package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.repository.SubjectRepository
import com.example.studyplanner.domain.repository.TaskRepository

class DeleteSubjectUseCase(
	private val repository: SubjectRepository,
	private val taskRepository: TaskRepository
) {
	suspend operator fun invoke(subject: Subject) {
		require(taskRepository.countBySubject(subject.id) == 0) {
			"No se puede eliminar: la materia tiene tareas asociadas"
		}
		repository.deleteSubject(subject)
	}
}
