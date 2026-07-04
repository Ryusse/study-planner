package com.example.studyplanner.di

import android.content.Context
import androidx.room.Room
import com.example.studyplanner.data.local.db.StudyPlannerDatabase
import com.example.studyplanner.data.repository.AchievementRepositoryImpl
import com.example.studyplanner.data.repository.FocusSessionRepositoryImpl
import com.example.studyplanner.data.repository.ProfessorRepositoryImpl
import com.example.studyplanner.data.repository.SubjectRepositoryImpl
import com.example.studyplanner.data.repository.TaskRepositoryImpl
import com.example.studyplanner.domain.repository.AchievementRepository
import com.example.studyplanner.domain.repository.FocusSessionRepository
import com.example.studyplanner.domain.repository.ProfessorRepository
import com.example.studyplanner.domain.repository.SubjectRepository
import com.example.studyplanner.domain.repository.TaskRepository
import com.example.studyplanner.domain.usecase.*
import com.example.studyplanner.presentation.viewmodel.ProfessorViewModel
import com.example.studyplanner.presentation.viewmodel.TaskViewModel
import com.example.studyplanner.presentation.viewmodel.FocusViewModel
import com.example.studyplanner.presentation.viewmodel.SubjectViewModel

object AppModule {
    @Volatile
    private var INSTANCE: StudyPlannerDatabase? = null

    private fun getDatabase(context: Context): StudyPlannerDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                   context.applicationContext,
                StudyPlannerDatabase::class.java,
                "study_planner_db"
                    ).build()
            INSTANCE = instance
            instance
        }
    }

    private fun provideProfessorRepository(context: Context): ProfessorRepository {
        val database = getDatabase(context)
        return ProfessorRepositoryImpl(database.professorDao())
    }

    private fun provideSubjectRepository(context: Context): SubjectRepository {
        val database = getDatabase(context)
        return SubjectRepositoryImpl(database.subjectDao())
    }

    private fun provideTaskRepository(context: Context): TaskRepository {
        val database = getDatabase(context)
        return TaskRepositoryImpl(database.taskDao())
    }

    private fun provideFocusSessionRepository(context: Context): FocusSessionRepository {
        val database = getDatabase(context)
        return FocusSessionRepositoryImpl(database.focusSessionDao())
    }

    private fun provideAchievementRepository(context: Context): AchievementRepository {
         val database = getDatabase(context)
        return AchievementRepositoryImpl(database.achievementDao())
    }

    fun provideProfessorUseCases(context: Context): ProfessorUseCases {
        val repository = provideProfessorRepository(context)
        return ProfessorUseCases(
            getAll = GetAllProfessorsUseCase(repository),
            add = AddProfessorUseCase(repository),
            update = UpdateProfessorUseCase(repository),
            delete = DeleteProfessorUseCase(repository)
        )
    }

    fun provideSubjectUseCases(context: Context): SubjectUseCases {
        val repository = provideSubjectRepository(context)
        return SubjectUseCases(
            getAll = GetAllSubjectsUseCase(repository),
            getByProfessor = GetSubjectsByProfessorUseCase(repository),
            add = AddSubjectUseCase(repository),
            delete = DeleteSubjectUseCase(repository)
        )
    }

    fun provideTaskUseCases(context: Context): TaskUseCases {
        val repository = provideTaskRepository(context)
        return TaskUseCases(
            create = CreateTaskUseCase(repository),
            delete = DeleteTaskUseCase(repository),
            getAll = GetAllTasksUseCase(repository),
            getPending = GetPendingTasksUseCase(repository),
            getUrgent = GetUrgentTasksUseCase(repository)
        )
    }

    fun provideFocusSessionUseCases(context: Context): FocusSessionUseCases {
        val focusRepository = provideFocusSessionRepository(context)
        val taskRepository = provideTaskRepository(context)
        return FocusSessionUseCases(
            getByTask = GetSessionsByTaskUseCase(focusRepository),
            getValidated = GetValidatedSessionsUseCase(focusRepository),
            create = CreateSessionUseCase(focusRepository),
            validate = ValidateSessionUseCase(focusRepository),
            getStreak = GetStreakUseCase(focusRepository),
            calculatePoints = CalculatePointsUseCase(focusRepository),
            getPendingCount = GetPendingTaskCountUseCase(taskRepository),
            getActiveSession = GetActiveSessionStateUseCase(focusRepository)
        )
    }

    fun provideAchievementUseCases(context: Context): AchievementUseCases {
        val repository = provideAchievementRepository(context)
        return AchievementUseCases(
            getAll = GetAllAchievementsUseCase(repository),
            getUnlocked = GetUnlockedAchievementsUseCase(repository),
            unlock = UnlockAchievementUseCase(repository)
        )
    }

    fun provideProfessorViewModel(context: Context): ProfessorViewModel {
        val useCases = provideProfessorUseCases(context)
        return ProfessorViewModel(useCases)
    }

    fun provideTaskViewModel(context: Context): TaskViewModel {
        val useCases = provideTaskUseCases(context)
        return TaskViewModel(useCases)
    }

    fun provideFocusViewModel(context: Context): FocusViewModel {
        val useCases = provideFocusSessionUseCases(context)
        return FocusViewModel(useCases)
    }

    fun provideSubjectViewModel(context: Context): SubjectViewModel {
        val useCases = provideSubjectUseCases(context)
        return SubjectViewModel(useCases)
    }
}
