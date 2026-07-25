package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Professor
import com.example.studyplanner.domain.repository.ProfessorRepository
import com.example.studyplanner.domain.repository.SubjectRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteProfessorUseCaseTest {
	private val repository: ProfessorRepository = mockk()
	private val subjectRepository: SubjectRepository = mockk()
	private lateinit var useCase: DeleteProfessorUseCase

	@Before
	fun setUp() {
		useCase = DeleteProfessorUseCase(repository, subjectRepository)
		coEvery { repository.deleteProfessor(any()) } returns Unit
	}

	@Test
	fun invoke_noSubjectsAssigned_deletes() = runTest {
		coEvery { subjectRepository.countByProfessor(1) } returns 0
		val professor = Professor(id = 1, name = "Carlos", email = "c@utp.edu.pe", department = "DS2")

		useCase(professor)

		coVerify { repository.deleteProfessor(professor) }
	}

	@Test(expected = IllegalArgumentException::class)
	fun invoke_hasSubjectsAssigned_throws() = runTest {
		coEvery { subjectRepository.countByProfessor(1) } returns 2
		val professor = Professor(id = 1, name = "Carlos", email = "c@utp.edu.pe", department = "DS2")

		useCase(professor)
	}
}
