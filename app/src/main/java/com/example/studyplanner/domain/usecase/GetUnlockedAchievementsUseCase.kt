package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Achievement
import com.example.studyplanner.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow

class GetUnlockedAchievementsUseCase(private val repository: AchievementRepository) {
	operator fun invoke(): Flow<List<Achievement>> = repository.getUnlockedAchievements()
}
