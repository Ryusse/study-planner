package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.domain.model.ProfessorPolicy
import com.example.studyplanner.domain.repository.ProfessorRepository

class UpdateProfessorUseCase(private val repository: ProfessorRepository) {
	suspend operator fun invoke(professor: Professor) {
		require(professor.name.length in ProfessorPolicy.NAME_MIN_LENGTH..ProfessorPolicy.NAME_MAX_LENGTH) {
			"El nombre debe tener entre ${ProfessorPolicy.NAME_MIN_LENGTH} y ${ProfessorPolicy.NAME_MAX_LENGTH} caracteres"
		}
		require(ProfessorPolicy.isValidEmail(professor.email)) { "Email inválido" }
		require(professor.department.isNotBlank()) { "Departamento requerido" }
		repository.updateProfessor(professor)
	}
}
