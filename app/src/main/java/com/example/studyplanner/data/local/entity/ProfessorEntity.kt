package com.example.studyplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professors")
data class ProfessorEntity(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val name: String,
	val email: String,
	val department: String
)
