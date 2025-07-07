package com.courses.internshipapp.modules.studentEvaluation

import com.courses.internshipapp.modules.evaluationCriteria.EvaluationCriteriaResponse
import java.time.LocalDate
import java.util.UUID

data class StudentEvaluationResponse(
    val studentEvaluationId: UUID,
    val date: LocalDate,
    val comments: String?,
    val criteria: List<EvaluationCriteriaResponse>
)
