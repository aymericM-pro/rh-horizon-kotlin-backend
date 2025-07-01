package com.courses.internshipapp.modules.internship.dtos

import com.courses.internshipapp.modules.internship.enums.*
import java.util.UUID

data class InternshipResponse(
    val internshipId: UUID?,
    val title: String,
    val company: String,
    val duration: Int,
    val year: StudentYear,
    val type: InternshipType,
    val major: Major,
    val validated: Boolean,
    val studentId: UUID?,
    val validatedBy: UUID?
)