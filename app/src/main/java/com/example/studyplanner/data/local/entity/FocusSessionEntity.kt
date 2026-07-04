package com.example.studyplanner.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
	tableName = "focus_sessions",
	foreignKeys = [
		ForeignKey(
			entity = TaskEntity::class,
			parentColumns = ["id"],
			childColumns = ["taskId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class FocusSessionEntity(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val taskId: Int,
	val cyclesCompleted: Int = 0,
	val startedAt: Long = System.currentTimeMillis(),
	val endedAt: Long? = null,
	val isValidated: Boolean = false,
	val validatedAt: Long? = null
)
