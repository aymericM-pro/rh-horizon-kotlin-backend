package com.courses.internshipapp.modules.evaluationCriteria

import java.time.LocalDate
import java.util.UUID

data class EvaluationCriteriaResponse(
    val evaluationCriteriaId: UUID,
    val label: String,
    val grades: Int
)