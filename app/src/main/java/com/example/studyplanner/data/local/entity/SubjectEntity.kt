package com.example.studyplanner.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
	tableName = "subjects",
	foreignKeys = [
		ForeignKey(
			entity = ProfessorEntity::class,
			parentColumns = ["id"],
			childColumns = ["professorId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class SubjectEntity(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val code: String,
	val name: String,
	val professorId: Int,
	val schedule: String,
	val color: String
)
