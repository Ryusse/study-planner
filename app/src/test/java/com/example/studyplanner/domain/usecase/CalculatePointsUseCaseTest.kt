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

class CalculatePointsUseCaseTest {
	private val repository: FocusSessionRepository = mockk()
	private lateinit var useCase: CalculatePointsUseCase

	@Before
	fun setUp() {
		useCase = CalculatePointsUseCase(repository)
	}

	@Test
	fun invoke_emptyList_returnsZero() = runTest {
		every { repository.getValidatedSessions() } returns flowOf(emptyList())

		useCase().collect { points ->
			assertEquals(0, points)
		}
	}

	@Test
	fun invoke_singleValidatedSession_returns15Points() = runTest {
		val now = System.currentTimeMillis()
		val sessions = listOf(
			FocusSession(id = 1, taskId = 1, isValidated = true, validatedAt = now)
		)
		every { repository.getValidatedSessions() } returns flowOf(sessions)

		useCase().collect { points ->
			assertEquals(15, points)
		}
	}
}
