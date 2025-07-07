package com.courses.internshipapp.modules.studentEvaluation


enum class ErrorStudentEvaluation(val code: String, val message: String) {
    NOT_FOUND("EVAL_001", "Évaluation introuvable"),
    INVALID_INPUT("EVAL_002", "Entrée invalide")
}
