package com.example.studyplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.studyplanner.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
	@Insert
	suspend fun insertSubject(subject: SubjectEntity)

	@Delete
	suspend fun deleteSubject(subject: SubjectEntity)

	@Update
	suspend fun updateSubject(subject: SubjectEntity)

	@Query("SELECT * FROM subjects")
	fun getAllSubjects(): Flow<List<SubjectEntity>>

	@Query("SELECT * FROM subjects WHERE id = :id")
	suspend fun getSubjectById(id: Int): SubjectEntity?

	@Query("SELECT * FROM subjects WHERE professorId = :professorId")
	fun getSubjectsByProfessor(professorId: Int): Flow<List<SubjectEntity>>
}
