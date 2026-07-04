package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Achievement
import com.example.studyplanner.domain.repository.AchievementRepository

class UnlockAchievementUseCase(private val repository: AchievementRepository) {
	suspend operator fun invoke(achievement: Achievement) = repository.updateAchievement(achievement)
}
