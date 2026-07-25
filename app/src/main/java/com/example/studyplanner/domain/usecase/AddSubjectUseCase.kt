package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.repository.SubjectRepository

class AddSubjectUseCase(private val repository: SubjectRepository) {
	suspend operator fun invoke(subject: Subject) {
		require(subject.code.isNotBlank()) { "Código requerido" }
		require(subject.name.isNotBlank()) { "Nombre requerido" }
		require(subject.schedule.isNotBlank()) { "Horario requerido" }
		require(repository.getSubjectByCode(subject.code) == null) { "Ya existe una materia con el código ${subject.code}" }
		repository.addSubject(subject)
	}
}
