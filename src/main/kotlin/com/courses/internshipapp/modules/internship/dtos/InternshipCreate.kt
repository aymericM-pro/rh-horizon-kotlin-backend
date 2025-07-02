package com.courses.internshipapp.modules.internship.dtos

import com.courses.internshipapp.modules.internship.enums.*
import java.util.UUID

data class InternshipCreate(
    val title: String,
    val description: String,
    val duration: Int,
    val startDate: String,
    val endDate: String,
    val year: StudentYear,
    val type: InternshipType,
    val major: Major,

    val company: CompanyDto,
    val supervisor: SupervisorDto,
    val documents: DocumentDto,
    val agreements: AgreementDto,

    val studentId: UUID,
    val skills: List<String>,
    val missions: List<String>
)
