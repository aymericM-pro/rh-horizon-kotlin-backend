package com.courses.internshipapp.modules.internship.dtos

import com.courses.internshipapp.modules.internship.entities.CompanyDetails
import java.util.UUID

data class InternshipCardDto(
    val internshipId: UUID,
    val title: String,
    val description: String,
    val status: String?,
    val company: CompanyDetails,
    val skills: List<String>
)