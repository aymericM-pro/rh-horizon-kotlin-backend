package com.courses.internshipapp.modules.studentEvaluation

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StudentEvaluationRepository: JpaRepository<StudentEvaluationEntity, UUID> {
}