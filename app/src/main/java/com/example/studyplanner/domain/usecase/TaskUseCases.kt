package com.example.studyplanner.domain.usecase

data class TaskUseCases(
	val create: CreateTaskUseCase,
	val delete: DeleteTaskUseCase,
	val getAll: GetAllTasksUseCase,
	val getPending: GetPendingTasksUseCase,
	val getUrgent: GetUrgentTasksUseCase
)
