package com.example.studyplanner.domain.usecase

data class SubjectUseCases(
	val getAll: GetAllSubjectsUseCase,
	val getByProfessor: GetSubjectsByProfessorUseCase,
	val add: AddSubjectUseCase,
	val update: UpdateSubjectUseCase,
	val delete: DeleteSubjectUseCase
)
