package com.courses.internshipapp.modules.defenses

import com.courses.internshipapp.modules.defenses.DefenseEntity
import com.courses.internshipapp.modules.internship.InternshipEntity

object DefenseMapper {

    fun toEntity(dto: DefenseCreate, internship: InternshipEntity) = DefenseEntity(
        preferredDate = dto.preferredDate,
        preferredTime = dto.preferredTime,
        alternativeDate = dto.alternativeDate,
        alternativeTime = dto.alternativeTime,
        presentationTitle = dto.presentationTitle,
        comments = dto.comments,
        internship = internship
    )

    fun toResponse(entity: DefenseEntity) = DefenseResponse(
        defenseId = entity.defenseId!!,
        preferredDate = entity.preferredDate,
        preferredTime = entity.preferredTime,
        alternativeDate = entity.alternativeDate,
        alternativeTime = entity.alternativeTime,
        presentationTitle = entity.presentationTitle,
        comments = entity.comments,
        internshipId = entity.internship.internshipId!!
    )
}
