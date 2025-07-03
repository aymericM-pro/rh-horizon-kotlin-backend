package com.courses.internshipapp.modules.defenses

import java.time.LocalDate

data class DefenseCreate(
    val preferredDate: LocalDate,
    val preferredTime: String,
    val alternativeDate: LocalDate?,
    val alternativeTime: String?,
    val presentationTitle: String,
    val comments: String?,
)