package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow

class GetAllSubjectsUseCase(private val repository: SubjectRepository) {
	operator fun invoke(): Flow<List<Subject>> = repository.getAllSubjects()
}
