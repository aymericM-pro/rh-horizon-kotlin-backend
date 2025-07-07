package com.courses.internshipapp.modules.studentEvaluation

import com.courses.internshipapp.modules.evaluationCriteria.EvaluationCriteriaEntity
import com.courses.internshipapp.modules.evaluationCriteria.EvaluationCriteriaMapper

object StudentEvaluationMapper {

    fun toEntity(request: StudentEvaluationCreateRequest): StudentEvaluationEntity {
        val evaluation = StudentEvaluationEntity(
            studentEvaluationId = request.studentEvaluationId,
            date = request.date,
            comments = request.comments
        )

        evaluation.criteria = request.criteria.map {
            EvaluationCriteriaEntity(
                label = it.label,
                grades = it.grades,
                evaluation = evaluation
            )
        }.toMutableList()

        return evaluation
    }

    fun toResponse(entity: StudentEvaluationEntity): StudentEvaluationResponse =
        StudentEvaluationResponse(
            studentEvaluationId = entity.studentEvaluationId!!,
            date = entity.date,
            comments = entity.comments,
            criteria = entity.criteria.map { EvaluationCriteriaMapper.toResponse(it) }
        )
}
