package com.example.studyplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.studyplanner.data.local.entity.ProfessorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfessorDao {
	@Insert
	suspend fun insertProfessor(professor: ProfessorEntity)

	@Delete
	suspend fun deleteProfessor(professor: ProfessorEntity)

	@Update
	suspend fun updateProfessor(professor: ProfessorEntity)

	@Query("SELECT * FROM professors")
	fun getAllProfessors(): Flow<List<ProfessorEntity>>

	@Query("SELECT * FROM professors WHERE id = :id")
	suspend fun getProfessorById(id: Int): ProfessorEntity?
}
