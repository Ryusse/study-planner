package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.domain.repository.ProfessorRepository
import com.example.studyplanner.domain.repository.SubjectRepository

class DeleteProfessorUseCase(
	private val repository: ProfessorRepository,
	private val subjectRepository: SubjectRepository
) {
	suspend operator fun invoke(professor: Professor) {
		require(subjectRepository.countByProfessor(professor.id) == 0) {
			"No se puede eliminar: el profesor tiene materias asignadas"
		}
		repository.deleteProfessor(professor)
	}
}
