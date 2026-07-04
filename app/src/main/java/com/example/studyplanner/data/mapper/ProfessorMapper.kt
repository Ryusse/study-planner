package com.example.studyplanner.data.mapper

import com.example.studyplanner.data.local.entity.ProfessorEntity
import com.example.studyplanner.domain.model.Professor

object ProfessorMapper {
	fun toDomain(entity: ProfessorEntity): Professor = Professor(
		id = entity.id,
		name = entity.name,
		email = entity.email,
		department = entity.department
	)

	fun toEntity(model: Professor): ProfessorEntity = ProfessorEntity(
		id = model.id,
		name = model.name,
		email = model.email,
		department = model.department
	)
}
