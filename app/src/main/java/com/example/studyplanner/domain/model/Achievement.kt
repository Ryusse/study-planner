package com.example.studyplanner.domain.model

data class Achievement(
	val id: Int = 0,
	val name: String,
	val description: String,
	val unlockedAt: Long? = null,
	val isUnlocked: Boolean = false
)
