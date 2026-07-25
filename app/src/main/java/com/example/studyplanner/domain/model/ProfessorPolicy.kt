package com.example.studyplanner.domain.model

object ProfessorPolicy {
	const val NAME_MIN_LENGTH = 3
	const val NAME_MAX_LENGTH = 100
	private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
	
	fun isValidEmail(email: String): Boolean = email.matches(EMAIL_REGEX)
}
