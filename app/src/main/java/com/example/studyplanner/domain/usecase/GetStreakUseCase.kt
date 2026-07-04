package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.repository.FocusSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetStreakUseCase(private val repository: FocusSessionRepository) {
	operator fun invoke(): Flow<Int> = repository.getValidatedSessions().map { sessions ->
		if (sessions.isEmpty()) return@map 0

		val sortedDates = sessions.mapNotNull { it.validatedAt }
			.distinct()
			.sorted()
			.map { it / 86400000 } // convert ms to days

		if (sortedDates.isEmpty()) return@map 0

		var streak = 1
		var lastDay = sortedDates.first()

		for (i in 1 until sortedDates.size) {
			if (sortedDates[i] - lastDay == 1L) {
				streak++
				lastDay = sortedDates[i]
			} else if (sortedDates[i] > lastDay + 1) {
				streak = 1
				lastDay = sortedDates[i]
			}
		}
		streak
	}
}
