package com.courses.internshipapp.modules.internship.dtos

import com.courses.internshipapp.modules.defenses.DefenseResponse
import com.courses.internshipapp.modules.internship.InternshipStatus
import com.courses.internshipapp.modules.internship.enums.*
import java.util.UUID

data class InternshipResponse(
    val internshipId: UUID?,
    val title: String,
    val description: String,
    val duration: Int,
    val startDate: String,
    val endDate: String,
    val year: StudentYear,
    val type: InternshipType,
    val major: Major,
    val validated: Boolean,
    val company: CompanyDto,
    val supervisor: SupervisorDto,
    val documents: DocumentDto,
    val agreements: AgreementDto,
    val status: InternshipStatus?,

    val studentId: UUID?,
    val validatedBy: UUID?,
    val skills: List<String>,
    val missions: List<String>,
    val defenses: DefenseResponse? = null
)
