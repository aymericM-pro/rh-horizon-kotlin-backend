package com.courses.internshipapp.modules.result

import com.courses.internshipapp.modules.defenses.DefenseEntity


object ResultMapper {

    fun toEntity(resultRequest: ResultCreate, defenseRequest: DefenseEntity): ResultEntity =
        ResultEntity(
            grade = resultRequest.grade,
            appreciation = resultRequest.appreciation,
            defense = defenseRequest
        )

    fun toResponse(entity: ResultEntity): ResultResponse =
        ResultResponse(
            id = entity.resultId ?: throw IllegalStateException("Result ID must not be null"),
            grade = entity.grade,
            appreciation = entity.appreciation
        )
}