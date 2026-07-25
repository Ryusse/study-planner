package com.example.studyplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.studyplanner.data.local.entity.FocusSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusSessionDao {
	@Insert
	suspend fun insertSession(session: FocusSessionEntity)

	@Delete
	suspend fun deleteSession(session: FocusSessionEntity)

	@Update
	suspend fun updateSession(session: FocusSessionEntity)

	@Query("SELECT * FROM focus_sessions ORDER BY startedAt DESC")
	fun getAllSessions(): Flow<List<FocusSessionEntity>>

	@Query("SELECT * FROM focus_sessions WHERE id = :id")
	suspend fun getSessionById(id: Int): FocusSessionEntity?

	@Query("SELECT * FROM focus_sessions WHERE taskId = :taskId")
	fun getSessionsByTask(taskId: Int): Flow<List<FocusSessionEntity>>

	@Query("SELECT * FROM focus_sessions WHERE isValidated = 1 ORDER BY validatedAt DESC")
	fun getValidatedSessions(): Flow<List<FocusSessionEntity>>

	@Query("SELECT COUNT(*) FROM focus_sessions WHERE isValidated = 1")
	suspend fun getValidatedSessionCount(): Int

	@Query("SELECT * FROM focus_sessions WHERE isValidated = 0 ORDER BY startedAt DESC")
	fun getActiveSessions(): Flow<List<FocusSessionEntity>>

	@Query("SELECT COUNT(*) FROM focus_sessions WHERE taskId = :taskId AND isValidated = 0")
	suspend fun countActiveByTask(taskId: Int): Int
}
