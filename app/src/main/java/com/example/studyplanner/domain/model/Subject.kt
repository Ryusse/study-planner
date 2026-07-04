package com.example.studyplanner.domain.model

data class Subject(
	val id: Int = 0,
	val code: String,
	val name: String,
	val professorId: Int,
	val schedule: String,
	val color: String
)
