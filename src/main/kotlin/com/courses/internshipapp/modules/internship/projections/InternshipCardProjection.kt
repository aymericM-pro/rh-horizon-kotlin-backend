package com.courses.internshipapp.modules.internship.projections

import java.util.UUID

interface InternshipCardWithSkillProjection {
    val internshipId: UUID
    val title: String?
    val description: String?
    val status: String?

    // Champs company.*
    val company_name: String?
    val company_sector: String?
    val company_size: String?
    val company_website: String?
    val company_address: String?
    val company_city: String?
    val company_postalCode: String?

    // Champ skill
    val skill_name: String?
}
