package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.FocusSession
import com.example.studyplanner.domain.repository.FocusSessionRepository
import kotlinx.coroutines.flow.Flow

class GetSessionsByTaskUseCase(private val repository: FocusSessionRepository) {
	operator fun invoke(taskId: Int): Flow<List<FocusSession>> = repository.getSessionsByTask(taskId)
}
