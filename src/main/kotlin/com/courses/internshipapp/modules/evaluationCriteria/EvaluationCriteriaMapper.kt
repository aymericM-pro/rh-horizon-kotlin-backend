package com.courses.internshipapp.modules.evaluationCriteria

import com.courses.internshipapp.modules.studentEvaluation.StudentEvaluationEntity

object EvaluationCriteriaMapper {

    fun toEntity(dto: EvaluationCriteriaCreateRequest, evaluation: StudentEvaluationEntity) =
        EvaluationCriteriaEntity(
            label = dto.label,
            grades = dto.grades,
            evaluation = evaluation
        )

    fun toResponse(entity: EvaluationCriteriaEntity) =
        EvaluationCriteriaResponse(
            evaluationCriteriaId = entity.evaluationCriteriaId!!,
            label = entity.label,
            grades = entity.grades
        )
}