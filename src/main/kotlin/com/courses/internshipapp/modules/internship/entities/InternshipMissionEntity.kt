package com.courses.internshipapp.modules.internship.entities

import com.courses.internshipapp.modules.internship.InternshipEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "internship_missions")
class InternshipMissionEntity(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, unique = true)
    var id: UUID? = null,

    @Column(nullable = false)
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id", nullable = false)
    var internship: InternshipEntity
)