package com.courses.internshipapp.modules.internship

import com.courses.internshipapp.modules.internship.dtos.*
import com.courses.internshipapp.modules.students.StudentEntity

object InternshipMapper {
    fun toEntity(request: InternshipCreate, student: StudentEntity): InternshipEntity = InternshipEntity(
        title = request.title,
        company = request.company,
        duration = request.duration,
        year = request.year,
        type = request.type,
        major = request.major,
        createdBy = student
    )

    fun toResponse(entity: InternshipEntity): InternshipResponse = InternshipResponse(
        internshipId = entity.internshipId,
        title = entity.title,
        company = entity.company,
        duration = entity.duration,
        year = entity.year,
        type = entity.type,
        major = entity.major,
        validated = entity.validated,
        studentId = entity.createdBy.userId,
        validatedBy = entity.validatedBy?.userId
    )
}
