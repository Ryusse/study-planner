package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.FocusSession
import com.example.studyplanner.domain.repository.FocusSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetActiveSessionStateUseCase(private val repository: FocusSessionRepository) {
	operator fun invoke(): Flow<FocusSession?> = repository.getActiveSessions().map { sessions ->
		sessions.filter { !it.isValidated }.maxByOrNull { it.startedAt }
	}
}
