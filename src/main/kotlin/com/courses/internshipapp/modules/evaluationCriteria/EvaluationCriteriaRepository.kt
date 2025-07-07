package com.courses.internshipapp.modules.evaluationCriteria

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface EvaluationCriteriaRepository: JpaRepository<EvaluationCriteriaEntity, UUID> {
}