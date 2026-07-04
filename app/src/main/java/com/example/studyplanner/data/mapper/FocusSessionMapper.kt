package com.example.studyplanner.data.mapper

import com.example.studyplanner.data.local.entity.FocusSessionEntity
import com.example.studyplanner.domain.model.FocusSession

object FocusSessionMapper {
	fun toDomain(entity: FocusSessionEntity): FocusSession = FocusSession(
		id = entity.id,
		taskId = entity.taskId,
		cyclesCompleted = entity.cyclesCompleted,
		startedAt = entity.startedAt,
		endedAt = entity.endedAt,
		isValidated = entity.isValidated,
		validatedAt = entity.validatedAt
	)

	fun toEntity(model: FocusSession): FocusSessionEntity = FocusSessionEntity(
		id = model.id,
		taskId = model.taskId,
		cyclesCompleted = model.cyclesCompleted,
		startedAt = model.startedAt,
		endedAt = model.endedAt,
		isValidated = model.isValidated,
		validatedAt = model.validatedAt
	)
}
