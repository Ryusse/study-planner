package com.example.studyplanner.data.mapper

import com.example.studyplanner.data.local.entity.TaskEntity
import com.example.studyplanner.domain.model.Task

object TaskMapper {
	fun toDomain(entity: TaskEntity): Task = Task(
		id = entity.id,
		subjectId = entity.subjectId,
		title = entity.title,
		deadline = entity.deadline,
		priority = entity.priority,
		type = entity.type,
		estimatedTime = entity.estimatedTime,
		isCompleted = entity.isCompleted,
		completedAt = entity.completedAt,
		createdAt = entity.createdAt
	)

	fun toEntity(model: Task): TaskEntity = TaskEntity(
		id = model.id,
		subjectId = model.subjectId,
		title = model.title,
		deadline = model.deadline,
		priority = model.priority,
		type = model.type,
		estimatedTime = model.estimatedTime,
		isCompleted = model.isCompleted,
		completedAt = model.completedAt,
		createdAt = model.createdAt
	)
}
