package com.example.studyplanner.data.repository

import com.example.studyplanner.data.local.dao.ProfessorDao
import com.example.studyplanner.data.mapper.ProfessorMapper
import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.domain.repository.ProfessorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfessorRepositoryImpl(private val dao: ProfessorDao) : ProfessorRepository {
	override suspend fun addProfessor(professor: Professor) {
		dao.insertProfessor(ProfessorMapper.toEntity(professor))
	}

	override suspend fun deleteProfessor(professor: Professor) {
		dao.deleteProfessor(ProfessorMapper.toEntity(professor))
	}

	override suspend fun updateProfessor(professor: Professor) {
		dao.updateProfessor(ProfessorMapper.toEntity(professor))
	}

	override fun getAllProfessors(): Flow<List<Professor>> {
		return dao.getAllProfessors().map { entities ->
			entities.map { ProfessorMapper.toDomain(it) }
		}
	}
}
