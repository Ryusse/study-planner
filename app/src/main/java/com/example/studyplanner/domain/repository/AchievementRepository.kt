package com.example.studyplanner.domain.repository

import com.example.studyplanner.domain.model.Achievement
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {
	suspend fun addAchievement(achievement: Achievement)
	suspend fun updateAchievement(achievement: Achievement)
	fun getAllAchievements(): Flow<List<Achievement>>
	fun getUnlockedAchievements(): Flow<List<Achievement>>
	suspend fun getAchievementById(id: Int): Achievement?
}
