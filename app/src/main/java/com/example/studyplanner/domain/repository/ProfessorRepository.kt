package com.example.studyplanner.domain.repository

import com.example.studyplanner.domain.model.Professor
import kotlinx.coroutines.flow.Flow

interface ProfessorRepository {
	suspend fun addProfessor(professor: Professor)
	suspend fun deleteProfessor(professor: Professor)
	suspend fun updateProfessor(professor: Professor)
	fun getAllProfessors(): Flow<List<Professor>>
}
