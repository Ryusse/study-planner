package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.FocusSession
import com.example.studyplanner.domain.repository.FocusSessionRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetStreakUseCaseTest {
	private val repository: FocusSessionRepository = mockk()
	private lateinit var useCase: GetStreakUseCase

	@Before
	fun setUp() {
		useCase = GetStreakUseCase(repository)
	}

	@Test
	fun invoke_emptyList_returnsZero() = runTest {
		every { repository.getValidatedSessions() } returns flowOf(emptyList())

		useCase().collect { streak ->
			assertEquals(0, streak)
		}
	}

	@Test
	fun invoke_singleSession_returnsOne() = runTest {
		val now = System.currentTimeMillis()
		val sessions = listOf(
			FocusSession(id = 1, taskId = 1, isValidated = true, validatedAt = now)
		)
		every { repository.getValidatedSessions() } returns flowOf(sessions)

		useCase().collect { streak ->
			assertEquals(1, streak)
		}
	}

	@Test
	fun invoke_consecutiveDays_countedCorrectly() = runTest {
		val baseDay = 18000L
		val sessions = listOf(
			FocusSession(id = 1, taskId = 1, isValidated = true, validatedAt = baseDay * 1000),
			FocusSession(id = 2, taskId = 1, isValidated = true, validatedAt = (baseDay + 86400) * 1000),
			FocusSession(id = 3, taskId = 1, isValidated = true, validatedAt = (baseDay + 86400 * 2) * 1000)
		)
		every { repository.getValidatedSessions() } returns flowOf(sessions)

		useCase().collect { streak ->
			assertEquals(3, streak)
		}
	}
}
