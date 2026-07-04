package com.example.studyplanner.domain.model

data class Task(
	val id: Int = 0,
	val subjectId: Int,
	val title: String,
	val deadline: Long,
	val priority: String,
	val type: String,
	val estimatedTime: Int,
	val isCompleted: Boolean = false,
	val completedAt: Long? = null,
	val createdAt: Long = System.currentTimeMillis()
)
