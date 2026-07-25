package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.SubjectRepository
import com.example.studyplanner.domain.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPendingTasksBySubjectUseCaseTest {
	private val taskRepository: TaskRepository = mockk()
	private val subjectRepository: SubjectRepository = mockk()
	private val useCase = GetPendingTasksBySubjectUseCase(taskRepository, subjectRepository)

	@Test
	fun invoke_groupsTasksBySubject_withCompletedAndPendingCounts() = runTest {
		val subject1 = Subject(id = 1, code = "IS101", name = "Móvil", professorId = 1, schedule = "Lun", color = "#FFFFFF")
		val subject2 = Subject(id = 2, code = "IS102", name = "Redes", professorId = 1, schedule = "Mar", color = "#000000")
		val tasks = listOf(
			Task(id = 1, subjectId = 1, title = "a", deadline = 0L, priority = "Alta", type = "Tarea", estimatedTime = 10, isCompleted = false),
			Task(id = 2, subjectId = 1, title = "b", deadline = 0L, priority = "Media", type = "Tarea", estimatedTime = 10, isCompleted = true),
			Task(id = 3, subjectId = 2, title = "c", deadline = 0L, priority = "Baja", type = "Examen", estimatedTime = 10, isCompleted = false)
		)
		every { taskRepository.getAllTasks() } returns flowOf(tasks)
		every { subjectRepository.getAllSubjects() } returns flowOf(listOf(subject1, subject2))

		useCase().collect { result ->
			assertEquals(2, result.size)
			assertEquals(1, result[subject1]?.pending)
			assertEquals(1, result[subject1]?.completed)
			assertEquals(1, result[subject2]?.pending)
			assertEquals(0, result[subject2]?.completed)
		}
	}

	@Test
	fun invoke_taskWithoutMatchingSubject_isExcluded() = runTest {
		val subject1 = Subject(id = 1, code = "IS101", name = "Móvil", professorId = 1, schedule = "Lun", color = "#FFFFFF")
		val tasks = listOf(
			Task(id = 1, subjectId = 99, title = "orphan", deadline = 0L, priority = "Alta", type = "Tarea", estimatedTime = 10)
		)
		every { taskRepository.getAllTasks() } returns flowOf(tasks)
		every { subjectRepository.getAllSubjects() } returns flowOf(listOf(subject1))

		useCase().collect { result ->
			assertEquals(0, result.size)
		}
	}
}
