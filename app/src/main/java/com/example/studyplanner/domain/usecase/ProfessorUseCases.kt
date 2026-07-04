package com.example.studyplanner.domain.usecase

data class ProfessorUseCases(
	val getAll: GetAllProfessorsUseCase,
	val add: AddProfessorUseCase,
	val update: UpdateProfessorUseCase,
	val delete: DeleteProfessorUseCase
)
