package com.courses.internshipapp.modules.evaluationCriteria

enum class ErrorEvaluationCriteria(val code: String, val message: String) {
    NOT_FOUND("CRITERIA_001", "Critère d’évaluation introuvable"),
    INVALID_INPUT("CRITERIA_002", "Entrée invalide pour le critère d’évaluation")
}
