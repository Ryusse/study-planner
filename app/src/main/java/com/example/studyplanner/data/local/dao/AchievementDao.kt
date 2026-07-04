package com.example.studyplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.studyplanner.data.local.entity.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
	@Insert
	suspend fun insertAchievement(achievement: AchievementEntity)

	@Update
	suspend fun updateAchievement(achievement: AchievementEntity)

	@Query("SELECT * FROM achievements")
	fun getAllAchievements(): Flow<List<AchievementEntity>>

	@Query("SELECT * FROM achievements WHERE isUnlocked = 1")
	fun getUnlockedAchievements(): Flow<List<AchievementEntity>>

	@Query("SELECT * FROM achievements WHERE id = :id")
	suspend fun getAchievementById(id: Int): AchievementEntity?
}
