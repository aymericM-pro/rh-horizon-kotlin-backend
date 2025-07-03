package com.courses.internshipapp.modules.defenses

import com.courses.internshipapp.modules.internship.InternshipEntity
import com.courses.internshipapp.modules.result.ResultEntity
import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name= "defenses")
class DefenseEntity (

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", unique = true, updatable = false)
    val defenseId: UUID? = null,

    @Column(name = "preferred_date", nullable = false)
    val preferredDate: LocalDate,

    @Column(name = "preferred_time", nullable = false)
    val preferredTime: String,

    @Column(name = "alternative_date")
    val alternativeDate: LocalDate? = null,

    @Column(name = "alternative_time")
    val alternativeTime: String? = null,

    @Column(name = "presentation_title", nullable = false)
    val presentationTitle: String,

    @Column(name = "comments", columnDefinition = "TEXT")
    val comments: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: DefenseStatus = DefenseStatus.PENDING_REQUEST,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id", nullable = false, unique = true)
    val internship: InternshipEntity,

    @OneToOne(mappedBy = "defense", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var resultEntity: ResultEntity? = null
)