package com.example.studyplanner.domain.usecase

data class FocusSessionUseCases(
	val getByTask: GetSessionsByTaskUseCase,
	val getValidated: GetValidatedSessionsUseCase,
	val create: CreateSessionUseCase,
	val validate: ValidateSessionUseCase,
	val getStreak: GetStreakUseCase,
	val calculatePoints: CalculatePointsUseCase,
	val getPendingCount: GetPendingTaskCountUseCase,
	val getActiveSession: GetActiveSessionStateUseCase
)
