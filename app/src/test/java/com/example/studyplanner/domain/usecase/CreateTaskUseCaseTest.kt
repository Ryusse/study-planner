package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateTaskUseCaseTest {
	private val repository: TaskRepository = mockk()
	private lateinit var useCase: CreateTaskUseCase

	@Before
	fun setUp() {
		useCase = CreateTaskUseCase(repository)
		coEvery { repository.addTask(any()) } returns Unit
	}

	@Test
	fun invoke_deadlineToday_isAccepted() = runTest {
		val task = Task(subjectId = 1, title = "t", deadline = System.currentTimeMillis(), priority = "Alta", type = "Tarea", estimatedTime = 30)

		useCase(task)

		coVerify { repository.addTask(task) }
	}

	@Test(expected = IllegalArgumentException::class)
	fun invoke_deadlineInThePast_throws() = runTest {
		val yesterday = System.currentTimeMillis() - 2 * 86_400_000L
		val task = Task(subjectId = 1, title = "t", deadline = yesterday, priority = "Alta", type = "Tarea", estimatedTime = 30)

		useCase(task)
	}
}
