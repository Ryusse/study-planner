# StudyPlanner — Proyecto Integrador Desarrollo Móvil

**Versión:** v1.0  
**Fecha:** 2026-06-29  
**Estado:** MVP (Minimum Viable Product)

---

## 1. Definición del Proyecto

### 1.1 Justificación

Estudiantes UTP dedican 8+ horas/semana a gestionar tareas en 5+ materias.
Resultado: falta de enfoque, inconsistencia, abandono de estudio.

StudyPlanner: un entrenador de consistencia. Pomodoro + racha visible transforma "tengo que estudiar" en "no quiero perder mi racha de 12 días".

### 1.2 Problema

- Estudiantes UTP tienen múltiples tareas en diferentes materias
- Dificultad para priorizar y completar a tiempo
- Falta de seguimiento visual del esfuerzo de estudio
- Poco feedback motivacional sobre progreso

### 1.3 Solución propuesta

App SQLite con:
- CRUD profesores, materias, tareas
- Timer Pomodoro 100% ortodoxo (25/5 min, pausa larga 15 min cada 4 ciclos)
- Vinculado a tarea específica (1 tarea = 1 sesión Pomodoro)
- Validación manual (usuario confirma si realmente trabajó)
- Dashboard con racha, badges, puntos (gamificación confiable)
- Historial sesiones: solo cuenta si validadas

### 1.3.1 Mapeo: Problema → requisito funcional

| Problema | Descripción | RF(s) que lo resuelven |
|---|---|---|
| P1 | Múltiples tareas en diferentes materias | RF-005, RF-006, RF-009, RF-010 |
| P2 | Dificultad para priorizar | RF-010 (filtro prioridad, ordenado deadline) |
| P3 | Falta seguimiento de esfuerzo | RF-014 a RF-021 (Pomodoro), RF-016 (Historial) |
| P4 | Visibilidad de progreso | RF-022, RF-023 (Dashboard: tareas pendientes por materia) |

### 1.4 Alcance (MVP)

#### ✓ Incluye:

- 3 CRUD completos (Profesores, Materias, Tareas)
- Pomodoro 100% ortodoxo (25/5 min, pausa larga 15 min cada 4 ciclos)
- Vinculado a tarea específica (1 tarea = 1 sesión)
- Validación manual sesiones (sí/no al terminar)
- Historial Pomodoro (solo sesiones validadas)
- Dashboard simple (tareas pendientes por materia)
- Filtrado básico (por materia, deadline, prioridad)

#### ✗ No incluye:

- Gamificación (racha, badges, puntos) — v2.0+
- Pomodoro libre (no ortodoxo)
- Extensión +5 min (no Pomodoro)
- Calendario (visualización removida)
- Notificaciones/recordatorios
- Análisis profundo (gráficos comparativos)
- Sincronización cloud
- Colaboración grupos

### 1.5 Limitaciones y restricciones

| Caso | Comportamiento |
|------|---|
| Reanudar sesión interrumpida | Si usuario abandona/app crash mid-sesión → sesión se descarta (sin guardar progreso) |
| Historial incompleto | Solo sesiones que completan 4 ciclos y llegan a dialog validación se persisten |
| Recuperación graceful | No hay "sesión en espera" → abandonada = perdida (v2.0+) |

### 1.6 Usuarios objetivo

Estudiantes UTP (Ingeniería de Software y afines), cualquier carrera técnica

---

## 2. Requisitos Funcionales

### 2.1 Gestión de Profesores

| ID | Funcionalidad | Descripción |
|---|---|---|
| RF-001 | Crear profesor | Nombre, email, departamento |
| RF-002 | Listar profesores | Con cantidad materias asignadas |
| RF-003 | Editar profesor | Modificar datos |
| RF-004 | Eliminar profesor | Con confirmación |

### 2.2 Gestión de Materias

| ID | Funcionalidad | Descripción |
|---|---|---|
| RF-005 | Crear materia | Código (IS101), nombre, profesor, horario, color |
| RF-006 | Listar materias | Con tareas pendientes, código color |
| RF-007 | Editar materia | Modificar datos |
| RF-008 | Eliminar materia | Con confirmación |

### 2.3 Gestión de Tareas

| ID | Funcionalidad | Descripción |
|---|---|---|
| RF-009 | Crear tarea | Materia, título, deadline, prioridad, tipo (tarea/examen/proyecto), tiempo estimado |
| RF-010 | Listar tareas | Filtrar por materia/deadline/prioridad/estado |
| RF-011 | Editar tarea | Modificar campos |
| RF-012 | Eliminar tarea | Con confirmación |
| RF-013 | Marcar completado | Cambiar estado, guardar fecha |

### 2.4 Sesiones de Estudio Pomodoro

| ID | Funcionalidad | Descripción |
|---|---|---|
| RF-014 | Timer Pomodoro | 25min trabajo + 5min pausa (fijo, no customizable) |
| RF-015 | Pausa larga | 15 min automática después 4 ciclos completados |
| RF-016 | Historial sesiones | Guardar ciclos, fecha, validación (solo sesiones completadas) |
| RF-017 | Iniciar focus | Tarea específica solo, jala materia + profesor automático |
| RF-018 | Validación sesión | Al terminar 4 ciclos: "¿Realmente completaste?" sí/no |
| RF-019 | 1 tarea = 1 sesión | 1 Pomodoro (25+5+25+5+25+5+25+15) vinculado a tarea |
| RF-020 | Sesión incompleta: abandono | Si usuario cierra app mid-sesión (ciclo <4): sesión se descarta (no se guarda) |
| RF-021 | Sesión incompleta: validada no | Si usuario llega a dialog pero valida NO: sesión se descarta (no se guarda) |

### 2.5 Dashboard

| ID | Funcionalidad | Descripción |
|---|---|---|
| RF-022 | Tareas pendientes por materia | Muestra contador de tareas no completadas por cada materia |
| RF-023 | Estado general | Display simple: total tareas, total completadas, pendientes |

---

## 3. Requisitos No Funcionales (ISO 25010)

### 3.1 Usabilidad
- Interfaz Material Design 3
- Flujos intuitivos (<3 taps por acción)
- Validación clara formularios
- Loading states visibles
- Colores por materia consistentes

### 3.2 Confiabilidad
- Persistencia SQLite sin pérdida data
- Recuperación errores graceful
- Transacciones atómicas

### 3.3 Performance
- **Carga lista tareas:** <1s (para 200 tareas)
- **Carga dashboard:** <500ms
- **Timer Pomodoro:** 60 FPS sin lageos
- **Animaciones UI:** Transiciones suaves (<300ms)
- **DB:** Indexada por `deadline`, `materia_id`, `estado`
- **Memoria:** <150MB en uso (sin leaks)

### 3.4 Compatibilidad
- Min SDK: 30
- Target SDK: 36
- Orientación: portrait + landscape

### 3.5 Testabilidad y Mantenibilidad
- Cobertura mínima: 80% (UseCase + Repository)
- Tests: JUnit 4 + MockK (domain + data)
- Integración: Espresso para Composables críticas
- Lógica de negocio centralizada (UseCase, no ViewModel)
- Inyección manual de dependencias (testeable sin Hilt)

---

## 4. Criterios de aceptación (muestra)

**RF-014: Timer Pomodoro (trabajo 25 min)**

Aceptado cuando:
- [ ] Timer inicia en 25:00
- [ ] Countdown decrementa cada segundo (visible en UI)
- [ ] Pausa/reanuda funciona sin perder tiempo
- [ ] Al llegar a 00:00, transiciona automáticamente a pausa 5min
- [ ] Sonido/vibración al final de ciclo
- [ ] Estado persiste en ViewModel (sin perder si app minimiza)

**RF-018: Validación de sesión (dialog sí/no)**

Aceptado cuando:
- [ ] Después de 4 ciclos, aparece dialog modal
- [ ] Texto: "¿Completaste los 4 ciclos Pomodoro?"
- [ ] botón "sí": guarda sesión en BD (validated=true)
- [ ] botón "No": descarta sesión en memoria (sin guardar)
- [ ] Ambos botones cierran dialog y limpian estado
- [ ] ViewModel `sessionState` retorna a inicial

**RF-022: racha focus (días consecutivos)**

Aceptado cuando:
- [ ] Contador incrementa +1 si sesión validada hoy
- [ ] Si no hay sesión validada mañana, racha resetea a 0
- [ ] Histórico: almacena último día con sesión validada
- [ ] Dashboard muestra "racha: X días" (0 si resetea)
- [ ] Testing: simular 7 días consecutivos → racha=7

---

## 5. Módulos del Proyecto (5)

### Módulo 1: Profesores (CRUD)
- Crear profesor (nombre, email, departamento)
- Listar con cantidad materias
- Editar/eliminar
- Búsqueda por nombre

### Módulo 2: Materias (CRUD)
- Crear (código, nombre, profesor, horario, color)
- Listar (con badge tareas pendientes)
- Editar/eliminar
- Filtro por profesor

### Módulo 3: Tareas (CRUD)
- Crear (materia, título, deadline, prioridad, tipo, tiempo_estimado)
- Listar (ordenado deadline)
- Editar/eliminar
- Marcar completado/incompleto
- Filtro: materia, deadline, prioridad, estado

### Módulo 4: Sesiones de Estudio con Técnica Pomodoro
- Iniciar sesión: Tarea específica obligatoria (materia, profesor se jalan automático)
- Timer ciclos: 25 min trabajo + 5 min pausa (fijo, no customizable)
- Pausa larga: 15 min automática después 4 ciclos completados
- 1 sesión Pomodoro = 4 ciclos (2h5min): 25+5+25+5+25+5+25+15
- Post-sesión dialog: "¿Realmente completaste?" (sí/no)
- Guardar historial: Solo si validado como sí
- Pantalla focus (minimizada, sin distracciones, tarea + materia + profesor)

### Módulo 5: Dashboard

**Pantalla principal:**

- Total tareas: completadas y pendientes (resumen por estado)
- Tareas pendientes por materia (lista agrupada, contador por materia)
- botón acceso directo a TaskListScreen completo

**Secciones:**
1. **Estado general:** Contador total tareas, completadas, pendientes
2. **Desglose por materia:** Cada materia con su contador de pendientes
3. **Acción:** botón "VER TODAS LAS TAREAS" → abre TaskListScreen

---

## 6. Mapeo de casos de uso (UseCase Map)

| RF | Nombre UseCase | Responsabilidad | Validación de aceptación |
|---|---|---|---|
| RF-001 | CreateProfessorUseCase | Crear profesor (nombre, email, depto) | Email válido, nombre no vacío |
| RF-002 | GetProfessorsUseCase | Listar profesores con contador materias | Retorna Flow<List<Professor>> |
| RF-003 | UpdateProfessorUseCase | Editar datos profesor | Sin validación especial |
| RF-004 | DeleteProfessorUseCase | Eliminar profesor (confirmación) | Solo si no tiene materias activas |
| RF-005 | CreateSubjectUseCase | Crear materia (código, nombre, prof, color) | Código único, nombre no vacío |
| RF-006 | GetSubjectsUseCase | Listar materias con badge tareas | Retorna Flow<List<Subject>> |
| RF-007 | UpdateSubjectUseCase | Editar materia | Validar ref. profesor existe |
| RF-008 | DeleteSubjectUseCase | Eliminar materia (confirmación) | Solo si no tiene tareas |
| RF-009 | CreateTaskUseCase | Crear tarea (materia, deadline, prioridad) | Deadline > hoy, materia existe |
| RF-010 | GetTasksUseCase | Listar + filtrar (materia, deadline, prioridad) | Retorna Flow<List<Task>> ordenado |
| RF-011 | UpdateTaskUseCase | Editar tarea | Validar estado transición |
| RF-012 | DeleteTaskUseCase | Eliminar tarea (confirmación) | Solo si no hay sesión activa |
| RF-013 | MarkTaskCompleteUseCase | Cambiar estado tarea + fecha | Transición estado válida |
| RF-014 | StartPomodoroSessionUseCase | Iniciar timer (25/5/25/5/25/5/25/15 min) | Tarea asignada, retorna SessionId |
| RF-015 | CompletePomodoroSessionUseCase | Completar sesión + mostrar dialog | Después 4 ciclos, sí/no |
| RF-016 | GetSessionHistoryUseCase | Historial sesiones (solo validadas) | Retorna Flow<List<Session>> |
| RF-017 | LoadTaskForSessionUseCase | Cargar tarea + materia + profesor | Datos completos precargados |
| RF-018 | ValidateSessionUseCase | dialog "¿Realmente completaste?" | Sí → guarda, No → descarta |
| RF-019 | GetActiveSessionUseCase | Obtener sesión en curso | Null si no hay activa |
| RF-020 | AbandonSessionUseCase | Descartar si abandonada mid-sesión | Sin persistencia |
| RF-021 | RejectSessionValidationUseCase | Descartar si validada como no | Limpia sin BD |
| RF-022 | GetPendingTasksBySubjectUseCase | Tareas pendientes agrupadas por materia | Retorna Flow<Map<Subject, List<Task>>> |
| RF-023 | GetTaskStatsUseCase | Estadísticas: total, completadas, pendientes | Retorna TaskStats object |

---

## 7. Arquitectura Clean Architecture + MVVM

### Capas

| Capa | Componentes |
|------|---|
| **Presentation** | Screens, ViewModels, State, Components, Navigation |
| **Domain** | Models, Repository interfaces, Use Cases |
| **Data** | Entities, DAOs, Database, RepositoryImpl, Mappers |
| **Core** | Navigation, Theme, UI Kit |
| **DI** | AppModule |

---

## 8. Stack técnico

| Componente | Tecnología |
|---|---|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material Design 3 |
| DB | Room ORM + SQLite |
| Async | Coroutines + Flow/StateFlow |
| Navigation | Compose Navigation |
| ViewModel | androidx.lifecycle |
| DI | Manual AppModule |
| Testing | JUnit 4 + Espresso + MockK |
| Build | Gradle 9.1.1 |

