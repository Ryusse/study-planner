package com.example.studyplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.studyplanner.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
	@Insert
	suspend fun insertTask(task: TaskEntity)

	@Delete
	suspend fun deleteTask(task: TaskEntity)

	@Update
	suspend fun updateTask(task: TaskEntity)

	@Query("SELECT * FROM tasks ORDER BY deadline ASC")
	fun getAllTasks(): Flow<List<TaskEntity>>

	@Query("SELECT * FROM tasks WHERE id = :id")
	suspend fun getTaskById(id: Int): TaskEntity?

	@Query("SELECT * FROM tasks WHERE subjectId = :subjectId ORDER BY deadline ASC")
	fun getTasksBySubject(subjectId: Int): Flow<List<TaskEntity>>

	@Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY deadline ASC")
	fun getPendingTasks(): Flow<List<TaskEntity>>

	@Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY deadline ASC LIMIT :limit")
	fun getUrgentTasks(limit: Int): Flow<List<TaskEntity>>

	@Query("SELECT COUNT(*) FROM tasks WHERE subjectId = :subjectId")
	suspend fun countBySubject(subjectId: Int): Int
}
