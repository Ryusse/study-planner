package com.example.studyplanner.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
import com.example.studyplanner.presentation.viewmodel.DashboardViewModel
import com.example.studyplanner.presentation.viewmodel.ProfessorViewModel
import com.example.studyplanner.presentation.viewmodel.TaskViewModel
import com.example.studyplanner.presentation.viewmodel.FocusViewModel
import com.example.studyplanner.presentation.viewmodel.SubjectViewModel

object AppModule {
    @Volatile
    private var INSTANCE: StudyPlannerDatabase? = null

    // ponytail: data de ejemplo, se inserta solo una vez cuando Room crea la BD por primera vez
    private val SEED_CALLBACK = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            db.execSQL("INSERT INTO professors (id, name, email, department) VALUES (1, 'Prof. Carlos Ramírez', 'carlos.ramirez@utp.edu.pe', 'Desarrollo de Software 2')")
            db.execSQL("INSERT INTO professors (id, name, email, department) VALUES (2, 'Prof. Lucía Fernández', 'lucia.fernandez@utp.edu.pe', 'Calidad de Software')")
            db.execSQL("INSERT INTO professors (id, name, email, department) VALUES (3, 'Prof. Jorge Salazar', 'jorge.salazar@utp.edu.pe', 'Desarrollo Móvil')")
            db.execSQL("INSERT INTO professors (id, name, email, department) VALUES (4, 'Prof. Patricia Gómez', 'patricia.gomez@utp.edu.pe', 'Formación para la Investigación')")
            db.execSQL("INSERT INTO professors (id, name, email, department) VALUES (5, 'Prof. Diego Torres', 'diego.torres@utp.edu.pe', 'Diseño de Videojuegos')")
            db.execSQL("INSERT INTO professors (id, name, email, department) VALUES (6, 'Prof. Andrea Quispe', 'andrea.quispe@utp.edu.pe', 'Redes y Comunicaciones')")
            db.execSQL("INSERT INTO professors (id, name, email, department) VALUES (7, 'Prof. Michael Johnson', 'michael.johnson@utp.edu.pe', 'English for Business')")

            db.execSQL("INSERT INTO subjects (id, code, name, professorId, schedule, color) VALUES (1, 'DS2-101', 'Desarrollo de Software 2', 1, 'Lunes 18:00-21:00', '#EF5350')")
            db.execSQL("INSERT INTO subjects (id, code, name, professorId, schedule, color) VALUES (2, 'CS-201', 'Calidad de Software', 2, 'Martes 18:00-21:00', '#EC407A')")
            db.execSQL("INSERT INTO subjects (id, code, name, professorId, schedule, color) VALUES (3, 'DM-301', 'Desarrollo Móvil', 3, 'Miércoles 19:00-21:00', '#AB47BC')")
            db.execSQL("INSERT INTO subjects (id, code, name, professorId, schedule, color) VALUES (4, 'FIS-401', 'Formación para la Investigación de Sistemas', 4, 'Jueves 18:00-20:00', '#5C6BC0')")
            db.execSQL("INSERT INTO subjects (id, code, name, professorId, schedule, color) VALUES (5, 'DDJ-501', 'Diseño y Desarrollo de Juegos Interactivos I', 5, 'Viernes 18:00-20:00', '#42A5F5')")
            db.execSQL("INSERT INTO subjects (id, code, name, professorId, schedule, color) VALUES (6, 'RCD-601', 'Redes y Comunicación de Datos 2', 6, 'Sábado 08:00-11:00', '#26A69A')")
            db.execSQL("INSERT INTO subjects (id, code, name, professorId, schedule, color) VALUES (7, 'ENG-701', 'English for Business', 7, 'Sábado 11:00-13:00', '#66BB6A')")

            val now = System.currentTimeMillis()
            val day = 24L * 60 * 60 * 1000
            fun task(id: Int, subjectId: Int, title: String, deadlineOffsetDays: Long, priority: String, type: String, estimatedTime: Int, isCompleted: Boolean = false) {
                val deadline = now + deadlineOffsetDays * day
                val completedAt = if (isCompleted) "${now - day}" else "NULL"
                db.execSQL(
                    "INSERT INTO tasks (id, subjectId, title, deadline, priority, type, estimatedTime, isCompleted, completedAt, createdAt) " +
                        "VALUES ($id, $subjectId, '$title', $deadline, '$priority', '$type', $estimatedTime, ${if (isCompleted) 1 else 0}, $completedAt, $now)"
                )
            }
            task(1, 1, "Práctica calificada 1", 3, "Alta", "Examen", 90)
            task(2, 2, "Entregable auditoría ONPE", 7, "Media", "Proyecto", 120)
            task(3, 3, "Terminar PC02 StudyPlanner", 5, "Alta", "Proyecto", 180)
            task(4, 4, "Avance RSL", 10, "Media", "Tarea", 60)
            task(5, 5, "Borrador GDD", 14, "Baja", "Tarea", 45)
            task(6, 6, "Laboratorio VLAN", -2, "Media", "Tarea", 60, isCompleted = true)
            task(7, 7, "Presentación técnica", 6, "Alta", "Proyecto", 90)
            task(8, 1, "Revisión CMMI", 9, "Baja", "Tarea", 30)
        }
    }

    private fun getDatabase(context: Context): StudyPlannerDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                   context.applicationContext,
                StudyPlannerDatabase::class.java,
                "study_planner_db"
                    )
                .addCallback(SEED_CALLBACK)
                .build()
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
            update = UpdateSubjectUseCase(repository),
            delete = DeleteSubjectUseCase(repository)
        )
    }

    fun provideTaskUseCases(context: Context): TaskUseCases {
        val repository = provideTaskRepository(context)
        return TaskUseCases(
            create = CreateTaskUseCase(repository),
            update = UpdateTaskUseCase(repository),
            delete = DeleteTaskUseCase(repository),
            getAll = GetAllTasksUseCase(repository),
            getPending = GetPendingTasksUseCase(repository),
            getUrgent = GetUrgentTasksUseCase(repository),
            markComplete = MarkTaskCompleteUseCase(repository)
        )
    }

    fun provideDashboardUseCases(context: Context): DashboardUseCases {
        val taskRepository = provideTaskRepository(context)
        val subjectRepository = provideSubjectRepository(context)
        return DashboardUseCases(
            getStats = GetTaskStatsUseCase(taskRepository),
            getPendingBySubject = GetPendingTasksBySubjectUseCase(taskRepository, subjectRepository)
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

    fun provideDashboardViewModel(context: Context): DashboardViewModel {
        val useCases = provideDashboardUseCases(context)
        return DashboardViewModel(useCases)
    }
}
