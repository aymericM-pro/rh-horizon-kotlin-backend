package com.courses.internshipapp.modules.result

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID


interface ResultRepository : JpaRepository<ResultEntity, UUID> {
    fun findByDefenseDefenseId(defenseId: UUID): ResultEntity?
    fun existsByDefenseDefenseId(defenseId: UUID): Boolean
}