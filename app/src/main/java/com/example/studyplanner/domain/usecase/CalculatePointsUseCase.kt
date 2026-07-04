package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.repository.FocusSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CalculatePointsUseCase(private val repository: FocusSessionRepository) {
	operator fun invoke(): Flow<Int> = repository.getValidatedSessions().map { sessions ->
		var points = 0
		val validatedCount = sessions.count { it.isValidated }

		points += validatedCount * 10
		points += validatedCount * 5

		val uniqueDates = sessions
			.filter { it.isValidated }
			.mapNotNull { it.validatedAt }
			.map { it / 86400000 }
			.distinct()

		if (uniqueDates.size >= 7) {
			points += (uniqueDates.size / 7) * 20
		}

		points
	}
}
