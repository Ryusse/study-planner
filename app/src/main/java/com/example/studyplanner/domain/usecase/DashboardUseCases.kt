package com.example.studyplanner.domain.usecase

data class DashboardUseCases(
	val getStats: GetTaskStatsUseCase,
	val getPendingBySubject: GetPendingTasksBySubjectUseCase
)
