package com.courses.internshipapp.modules.defenses


import java.time.LocalDate
import java.util.UUID

data class DefenseResponse(
    val defenseId: UUID,
    val preferredDate: LocalDate,
    val preferredTime: String,
    val alternativeDate: LocalDate?,
    val alternativeTime: String?,
    val presentationTitle: String,
    val comments: String?,
    val internshipId: UUID
)