package com.example.studyplanner.domain.model

data class FocusSession(
	val id: Int = 0,
	val taskId: Int,
	val cyclesCompleted: Int = 0,
	val startedAt: Long = System.currentTimeMillis(),
	val endedAt: Long? = null,
	val isValidated: Boolean = false,
	val validatedAt: Long? = null
)
