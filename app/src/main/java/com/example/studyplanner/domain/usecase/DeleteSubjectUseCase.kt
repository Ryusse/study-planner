package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.repository.SubjectRepository

class DeleteSubjectUseCase(private val repository: SubjectRepository) {
	suspend operator fun invoke(subject: Subject) = repository.deleteSubject(subject)
}
