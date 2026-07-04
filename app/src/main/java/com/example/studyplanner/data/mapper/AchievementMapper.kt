package com.example.studyplanner.data.mapper

import com.example.studyplanner.data.local.entity.AchievementEntity
import com.example.studyplanner.domain.model.Achievement

object AchievementMapper {
	fun toDomain(entity: AchievementEntity): Achievement = Achievement(
		id = entity.id,
		name = entity.name,
		description = entity.description,
		unlockedAt = entity.unlockedAt,
		isUnlocked = entity.isUnlocked
	)

	fun toEntity(model: Achievement): AchievementEntity = AchievementEntity(
		id = model.id,
		name = model.name,
		description = model.description,
		unlockedAt = model.unlockedAt,
		isUnlocked = model.isUnlocked
	)
}
