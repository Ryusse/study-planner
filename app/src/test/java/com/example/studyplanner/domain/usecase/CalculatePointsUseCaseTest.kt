package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.FocusSession
import com.example.studyplanner.domain.repository.FocusSessionRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`

class CalculatePointsUseCaseTest {
	@Mock
	private lateinit var repository: FocusSessionRepository
	private lateinit var useCase: CalculatePointsUseCase

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		useCase = CalculatePointsUseCase(repository)
	}

	@Test
	fun invoke_emptyList_returnsZero() = runTest {
		`when`(repository.getValidatedSessions()).thenReturn(flowOf(emptyList()))

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
		`when`(repository.getValidatedSessions()).thenReturn(flowOf(sessions))

		useCase().collect { points ->
			assertEquals(15, points)
		}
	}
}
