package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.domain.repository.ProfessorRepository

class AddProfessorUseCase(private val repository: ProfessorRepository) {
	suspend operator fun invoke(professor: Professor) = repository.addProfessor(professor)
}
