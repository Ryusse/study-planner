package com.example.studyplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementEntity(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val name: String,
	val description: String,
	val unlockedAt: Long? = null,
	val isUnlocked: Boolean = false
)
