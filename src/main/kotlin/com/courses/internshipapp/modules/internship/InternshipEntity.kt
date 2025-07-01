package com.courses.internshipapp.modules.internship

import com.courses.internshipapp.modules.admin.AdminEntity
import com.courses.internshipapp.modules.internship.enums.*
import com.courses.internshipapp.modules.students.StudentEntity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "internships")
class InternshipEntity(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, unique = true)
    var internshipId: UUID? = null,

    var title: String,
    var company: String,
    var duration: Int,

    @Enumerated(EnumType.STRING)
    var year: StudentYear,

    @Enumerated(EnumType.STRING)
    var type: InternshipType,

    @Enumerated(EnumType.STRING)
    var major: Major,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    var createdBy: StudentEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validated_by")
    var validatedBy: AdminEntity? = null,

    @Column(nullable = false)
    var validated: Boolean = false,
)
