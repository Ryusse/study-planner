package com.example.studyplanner.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studyplanner.data.local.dao.AchievementDao
import com.example.studyplanner.data.local.dao.FocusSessionDao
import com.example.studyplanner.data.local.dao.ProfessorDao
import com.example.studyplanner.data.local.dao.SubjectDao
import com.example.studyplanner.data.local.dao.TaskDao
import com.example.studyplanner.data.local.entity.AchievementEntity
import com.example.studyplanner.data.local.entity.FocusSessionEntity
import com.example.studyplanner.data.local.entity.ProfessorEntity
import com.example.studyplanner.data.local.entity.SubjectEntity
import com.example.studyplanner.data.local.entity.TaskEntity

@Database(
	entities = [
		ProfessorEntity::class,
		SubjectEntity::class,
		TaskEntity::class,
		FocusSessionEntity::class,
		AchievementEntity::class
	],
	version = 1
)
abstract class StudyPlannerDatabase : RoomDatabase() {
	abstract fun professorDao(): ProfessorDao
	abstract fun subjectDao(): SubjectDao
	abstract fun taskDao(): TaskDao
	abstract fun focusSessionDao(): FocusSessionDao
	abstract fun achievementDao(): AchievementDao
}
