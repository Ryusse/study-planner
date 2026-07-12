package com.example.studyplanner.domain.usecase

import com.example.studyplanner.domain.model.Subject
import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.SubjectRepository
import com.example.studyplanner.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPendingTasksBySubjectUseCase(
	private val taskRepository: TaskRepository,
	private val subjectRepository: SubjectRepository
) {
	operator fun invoke(): Flow<Map<Subject, List<Task>>> =
		combine(
			taskRepository.getPendingTasks(),
			subjectRepository.getAllSubjects()
		) { pendingTasks, subjects ->
			val subjectsById = subjects.associateBy { it.id }
			pendingTasks
				.groupBy { it.subjectId }
				.mapNotNull { (subjectId, tasks) ->
					subjectsById[subjectId]?.let { subject -> subject to tasks }
				}
				.toMap()
		}
}
