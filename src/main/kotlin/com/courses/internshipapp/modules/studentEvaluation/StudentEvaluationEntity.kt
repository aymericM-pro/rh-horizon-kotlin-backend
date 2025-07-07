package com.courses.internshipapp.modules.studentEvaluation

import com.courses.internshipapp.modules.evaluationCriteria.EvaluationCriteriaEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "student_evaluation")
class StudentEvaluationEntity(

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", unique = true, updatable = false)
    val studentEvaluationId: UUID?,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = true, columnDefinition = "TEXT")
    var comments: String? = null,

    @OneToMany(mappedBy = "evaluation", cascade = [CascadeType.ALL], orphanRemoval = true)
    var criteria: MutableList<EvaluationCriteriaEntity> = mutableListOf()
)
