package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.model.SubjectProgress
import com.example.studyplanner.domain.repository.SubjectRepository
import com.example.studyplanner.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPendingTasksBySubjectUseCase(
	private val taskRepository: TaskRepository,
	private val subjectRepository: SubjectRepository
) {
	operator fun invoke(): Flow<Map<Subject, SubjectProgress>> =
		combine(
			taskRepository.getAllTasks(),
			subjectRepository.getAllSubjects()
		) { allTasks, subjects ->
			val subjectsById = subjects.associateBy { it.id }
			allTasks
				.groupBy { it.subjectId }
				.mapNotNull { (subjectId, tasks) ->
					subjectsById[subjectId]?.let { subject ->
						subject to SubjectProgress(
							completed = tasks.count { it.isCompleted },
							pending = tasks.count { !it.isCompleted }
						)
					}
				}
				.toMap()
		}
}
