package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTaskStatsUseCaseTest {
	private val repository: TaskRepository = mockk()
	private val useCase = GetTaskStatsUseCase(repository)

	@Test
	fun invoke_mixedTasks_countsCorrectly() = runTest {
		val tasks = listOf(
			Task(id = 1, subjectId = 1, title = "a", deadline = 0L, priority = "Alta", type = "Tarea", estimatedTime = 10, isCompleted = true),
			Task(id = 2, subjectId = 1, title = "b", deadline = 0L, priority = "Media", type = "Tarea", estimatedTime = 10, isCompleted = false),
			Task(id = 3, subjectId = 2, title = "c", deadline = 0L, priority = "Baja", type = "Examen", estimatedTime = 10, isCompleted = false)
		)
		every { repository.getAllTasks() } returns flowOf(tasks)

		useCase().collect { stats ->
			assertEquals(3, stats.total)
			assertEquals(1, stats.completed)
			assertEquals(2, stats.pending)
		}
	}
}
