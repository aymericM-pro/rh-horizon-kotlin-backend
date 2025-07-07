package com.courses.internshipapp.modules.studentEvaluation

import com.courses.internshipapp.modules.evaluationCriteria.EvaluationCriteriaCreateRequest
import java.time.LocalDate
import java.util.UUID

data class StudentEvaluationCreateRequest(
    val studentEvaluationId: UUID? = null,
    val date: LocalDate,
    val comments: String? = null,
    val criteria: List<EvaluationCriteriaCreateRequest>
)
