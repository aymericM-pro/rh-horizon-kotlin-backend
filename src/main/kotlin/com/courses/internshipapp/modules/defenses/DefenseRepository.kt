package com.courses.internshipapp.modules.defenses

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DefenseRepository : JpaRepository<DefenseEntity, UUID> {
    fun findByInternship_InternshipId(internshipId: UUID): DefenseEntity?
}
