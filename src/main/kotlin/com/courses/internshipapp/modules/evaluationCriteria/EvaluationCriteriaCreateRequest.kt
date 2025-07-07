package com.courses.internshipapp.modules.evaluationCriteria

data class EvaluationCriteriaCreateRequest(
    val label: String,
    val grades: Int
)