package com.example.studyplanner.data.repository

import com.example.studyplanner.data.local.dao.TaskDao
import com.example.studyplanner.data.mapper.TaskMapper
import com.example.studyplanner.domain.model.Task
import com.example.studyplanner.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(private val dao: TaskDao) : TaskRepository {
	override suspend fun addTask(task: Task) {
		dao.insertTask(TaskMapper.toEntity(task))
	}

	override suspend fun deleteTask(task: Task) {
		dao.deleteTask(TaskMapper.toEntity(task))
	}

	override suspend fun updateTask(task: Task) {
		dao.updateTask(TaskMapper.toEntity(task))
	}

	override fun getAllTasks(): Flow<List<Task>> {
		return dao.getAllTasks().map { entities ->
			entities.map { TaskMapper.toDomain(it) }
		}
	}

	override suspend fun getTaskById(id: Int): Task? {
		return dao.getTaskById(id)?.let { TaskMapper.toDomain(it) }
	}

	override fun getTasksBySubject(subjectId: Int): Flow<List<Task>> {
		return dao.getTasksBySubject(subjectId).map { entities ->
			entities.map { TaskMapper.toDomain(it) }
		}
	}

	override fun getPendingTasks(): Flow<List<Task>> {
		return dao.getPendingTasks().map { entities ->
			entities.map { TaskMapper.toDomain(it) }
		}
	}

	override fun getThreeUrgentTasks(): Flow<List<Task>> {
		return dao.getThreeUrgentTasks().map { entities ->
			entities.map { TaskMapper.toDomain(it) }
		}
	}
}
