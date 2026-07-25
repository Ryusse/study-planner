package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.repository.SubjectRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddSubjectUseCaseTest {
	private val repository: SubjectRepository = mockk()
	private lateinit var useCase: AddSubjectUseCase

	@Before
	fun setUp() {
		useCase = AddSubjectUseCase(repository)
		coEvery { repository.addSubject(any()) } returns Unit
	}

	@Test
	fun invoke_uniqueCode_isAccepted() = runTest {
		coEvery { repository.getSubjectByCode("IS101") } returns null
		val subject = Subject(code = "IS101", name = "Móvil", professorId = 1, schedule = "Lun", color = "#FFF")

		useCase(subject)

		coVerify { repository.addSubject(subject) }
	}

	@Test(expected = IllegalArgumentException::class)
	fun invoke_duplicateCode_throws() = runTest {
		val existing = Subject(id = 1, code = "IS101", name = "Móvil", professorId = 1, schedule = "Lun", color = "#FFF")
		coEvery { repository.getSubjectByCode("IS101") } returns existing
		val subject = Subject(code = "IS101", name = "Otra", professorId = 2, schedule = "Mar", color = "#000")

		useCase(subject)
	}
}
