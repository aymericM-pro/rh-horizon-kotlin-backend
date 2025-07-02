package com.courses.internshipapp.modules.skills

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SkillRepository: JpaRepository<SkillEntity, UUID> {
    fun existsByName(name: String): Boolean;
    fun findByName(name: String): SkillEntity?
}