package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MarkTaskCompleteUseCaseTest {
	private val repository: TaskRepository = mockk()
	private lateinit var useCase: MarkTaskCompleteUseCase

	@Before
	fun setUp() {
		useCase = MarkTaskCompleteUseCase(repository)
		coEvery { repository.updateTask(any()) } returns Unit
	}

	@Test
	fun invoke_completed_setsCompletedAtAndFlag() = runTest {
		val task = Task(id = 1, subjectId = 1, title = "t", deadline = 0L, priority = "Alta", type = "Tarea", estimatedTime = 30)

		useCase(task, completed = true)

		coVerify {
			repository.updateTask(withArg {
				assertTrue(it.isCompleted)
				assertNotNull(it.completedAt)
			})
		}
	}

	@Test
	fun invoke_notCompleted_clearsCompletedAt() = runTest {
		val task = Task(id = 1, subjectId = 1, title = "t", deadline = 0L, priority = "Alta", type = "Tarea", estimatedTime = 30, isCompleted = true, completedAt = 123L)

		useCase(task, completed = false)

		coVerify {
			repository.updateTask(withArg {
				assertTrue(!it.isCompleted)
				assertNull(it.completedAt)
			})
		}
	}
}
