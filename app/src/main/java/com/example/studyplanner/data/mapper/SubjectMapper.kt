package com.example.studyplanner.data.mapper

import com.example.studyplanner.data.local.entity.SubjectEntity
import com.example.studyplanner.domain.model.Subject

object SubjectMapper {
	fun toDomain(entity: SubjectEntity): Subject = Subject(
		id = entity.id,
		code = entity.code,
		name = entity.name,
		professorId = entity.professorId,
		schedule = entity.schedule,
		color = entity.color
	)

	fun toEntity(model: Subject): SubjectEntity = SubjectEntity(
		id = model.id,
		code = model.code,
		name = model.name,
		professorId = model.professorId,
		schedule = model.schedule,
		color = model.color
	)
}
