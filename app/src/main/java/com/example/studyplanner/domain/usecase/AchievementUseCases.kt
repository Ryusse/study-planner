package com.example.studyplanner.domain.usecase

data class AchievementUseCases(
	val getAll: GetAllAchievementsUseCase,
	val getUnlocked: GetUnlockedAchievementsUseCase,
	val unlock: UnlockAchievementUseCase
)
