package com.example.studyplanner.data.repository

import com.example.studyplanner.data.local.dao.SubjectDao
import com.example.studyplanner.data.mapper.SubjectMapper
import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectRepositoryImpl(private val dao: SubjectDao) : SubjectRepository {
	override suspend fun addSubject(subject: Subject) {
		dao.insertSubject(SubjectMapper.toEntity(subject))
	}

	override suspend fun deleteSubject(subject: Subject) {
		dao.deleteSubject(SubjectMapper.toEntity(subject))
	}

	override suspend fun updateSubject(subject: Subject) {
		dao.updateSubject(SubjectMapper.toEntity(subject))
	}

	override fun getAllSubjects(): Flow<List<Subject>> {
		return dao.getAllSubjects().map { entities ->
			entities.map { SubjectMapper.toDomain(it) }
		}
	}

	override suspend fun getSubjectById(id: Int): Subject? {
		return dao.getSubjectById(id)?.let { SubjectMapper.toDomain(it) }
	}

	override fun getSubjectsByProfessor(professorId: Int): Flow<List<Subject>> {
		return dao.getSubjectsByProfessor(professorId).map { entities ->
			entities.map { SubjectMapper.toDomain(it) }
		}
	}
}
