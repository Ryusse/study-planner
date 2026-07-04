package com.example.studyplanner.data.repository

import com.example.studyplanner.data.local.dao.AchievementDao
import com.example.studyplanner.data.mapper.AchievementMapper
import com.example.studyplanner.domain.model.Achievement
import com.example.studyplanner.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AchievementRepositoryImpl(private val dao: AchievementDao) : AchievementRepository {
	override suspend fun addAchievement(achievement: Achievement) {
		dao.insertAchievement(AchievementMapper.toEntity(achievement))
	}

	override suspend fun updateAchievement(achievement: Achievement) {
		dao.updateAchievement(AchievementMapper.toEntity(achievement))
	}

	override fun getAllAchievements(): Flow<List<Achievement>> {
		return dao.getAllAchievements().map { entities ->
			entities.map { AchievementMapper.toDomain(it) }
		}
	}

	override fun getUnlockedAchievements(): Flow<List<Achievement>> {
		return dao.getUnlockedAchievements().map { entities ->
			entities.map { AchievementMapper.toDomain(it) }
		}
	}

	override suspend fun getAchievementById(id: Int): Achievement? {
		return dao.getAchievementById(id)?.let { AchievementMapper.toDomain(it) }
	}
}
