package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.FocusSession
import com.example.studyplanner.domain.repository.FocusSessionRepository

class CreateSessionUseCase(private val repository: FocusSessionRepository) {
	suspend operator fun invoke(session: FocusSession) = repository.addSession(session)
}
