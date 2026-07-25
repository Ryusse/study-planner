package com.example.studyplanner.domain.repository

import com.example.studyplanner.domain.model.FocusSession
import kotlinx.coroutines.flow.Flow

interface FocusSessionRepository {
	suspend fun addSession(session: FocusSession)
	suspend fun deleteSession(session: FocusSession)
	suspend fun updateSession(session: FocusSession)
	fun getAllSessions(): Flow<List<FocusSession>>
	suspend fun getSessionById(id: Int): FocusSession?
	fun getSessionsByTask(taskId: Int): Flow<List<FocusSession>>
	fun getValidatedSessions(): Flow<List<FocusSession>>
	fun getActiveSessions(): Flow<List<FocusSession>>
	suspend fun getValidatedSessionCount(): Int
	suspend fun countActiveByTask(taskId: Int): Int
}
