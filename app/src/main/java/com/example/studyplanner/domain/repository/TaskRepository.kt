package com.example.studyplanner.domain.repository

import com.example.studyplanner.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
	suspend fun addTask(task: Task)
	suspend fun deleteTask(task: Task)
	suspend fun updateTask(task: Task)
	fun getAllTasks(): Flow<List<Task>>
	suspend fun getTaskById(id: Int): Task?
	fun getTasksBySubject(subjectId: Int): Flow<List<Task>>
	fun getPendingTasks(): Flow<List<Task>>
	fun getUrgentTasks(limit: Int): Flow<List<Task>>
	suspend fun countBySubject(subjectId: Int): Int
}
