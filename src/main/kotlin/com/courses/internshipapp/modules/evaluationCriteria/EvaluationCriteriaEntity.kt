package com.courses.internshipapp.modules.evaluationCriteria

import com.courses.internshipapp.modules.studentEvaluation.StudentEvaluationEntity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "evaluation_criteria")
class EvaluationCriteriaEntity (

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", unique = true, updatable = false)
    var evaluationCriteriaId: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", nullable = false)
    var evaluation: StudentEvaluationEntity,

    @Column(nullable = false)
    var label: String,

    @Column(nullable = false)
    var grades: Int
)
