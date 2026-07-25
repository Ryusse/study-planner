package com.example.studyplanner.data.repository

import com.example.studyplanner.data.local.dao.FocusSessionDao
import com.example.studyplanner.data.mapper.FocusSessionMapper
import com.example.studyplanner.domain.model.FocusSession
import com.example.studyplanner.domain.repository.FocusSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FocusSessionRepositoryImpl(private val dao: FocusSessionDao) : FocusSessionRepository {
	override suspend fun addSession(session: FocusSession) {
		dao.insertSession(FocusSessionMapper.toEntity(session))
	}

	override suspend fun deleteSession(session: FocusSession) {
		dao.deleteSession(FocusSessionMapper.toEntity(session))
	}

	override suspend fun updateSession(session: FocusSession) {
		dao.updateSession(FocusSessionMapper.toEntity(session))
	}

	override fun getAllSessions(): Flow<List<FocusSession>> {
		return dao.getAllSessions().map { entities ->
			entities.map { FocusSessionMapper.toDomain(it) }
		}
	}

	override suspend fun getSessionById(id: Int): FocusSession? {
		return dao.getSessionById(id)?.let { FocusSessionMapper.toDomain(it) }
	}

	override fun getSessionsByTask(taskId: Int): Flow<List<FocusSession>> {
		return dao.getSessionsByTask(taskId).map { entities ->
			entities.map { FocusSessionMapper.toDomain(it) }
		}
	}

	override fun getValidatedSessions(): Flow<List<FocusSession>> {
		return dao.getValidatedSessions().map { entities ->
			entities.map { FocusSessionMapper.toDomain(it) }
		}
	}

	override fun getActiveSessions(): Flow<List<FocusSession>> {
		return dao.getActiveSessions().map { entities ->
			entities.map { FocusSessionMapper.toDomain(it) }
		}
	}

	override suspend fun getValidatedSessionCount(): Int {
		return dao.getValidatedSessionCount()
	}

	override suspend fun countActiveByTask(taskId: Int): Int {
		return dao.countActiveByTask(taskId)
	}
}
