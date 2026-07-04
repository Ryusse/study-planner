package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.domain.repository.ProfessorRepository
import kotlinx.coroutines.flow.Flow

class GetAllProfessorsUseCase(private val repository: ProfessorRepository) {
	operator fun invoke(): Flow<List<Professor>> = repository.getAllProfessors()
}
