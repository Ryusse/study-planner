package com.example.studyplanner.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
	tableName = "tasks",
	foreignKeys = [
		ForeignKey(
			entity = SubjectEntity::class,
			parentColumns = ["id"],
			childColumns = ["subjectId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class TaskEntity(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
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
