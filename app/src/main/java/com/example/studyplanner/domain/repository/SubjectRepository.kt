package com.example.studyplanner.domain.repository

import com.example.studyplanner.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
	suspend fun addSubject(subject: Subject)
	suspend fun deleteSubject(subject: Subject)
	suspend fun updateSubject(subject: Subject)
	fun getAllSubjects(): Flow<List<Subject>>
	suspend fun getSubjectById(id: Int): Subject?
	fun getSubjectsByProfessor(professorId: Int): Flow<List<Subject>>
	suspend fun getSubjectByCode(code: String): Subject?
	suspend fun countByProfessor(professorId: Int): Int
}
