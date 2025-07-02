package com.courses.internshipapp.modules.internship

import com.courses.internshipapp.modules.admin.AdminEntity
import com.courses.internshipapp.modules.internship.entities.*
import com.courses.internshipapp.modules.internship.enums.*
import com.courses.internshipapp.modules.skills.SkillEntity
import com.courses.internshipapp.modules.students.StudentEntity
import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "internships")
class InternshipEntity(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, unique = true)
    var internshipId: UUID? = null,

    var title: String,
    var description: String,
    var duration: Int,

    var startDate: LocalDate,
    var endDate: LocalDate,

    @Enumerated(EnumType.STRING)
    var year: StudentYear,

    @Enumerated(EnumType.STRING)
    var type: InternshipType,

    @Enumerated(EnumType.STRING)
    var major: Major,

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    var status: InternshipStatus? = null,
    @Embedded
    var company: CompanyDetails,

    @Embedded
    var supervisor: SupervisorDetails,

    @Embedded
    var documents: DocumentLinks,

    @Embedded
    var agreements: AgreementStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    var createdBy: StudentEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validated_by")
    var validatedBy: AdminEntity? = null,

    @Column(nullable = false)
    var validated: Boolean = false,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "internship_skills",
        joinColumns = [JoinColumn(name = "internship_id")],
        inverseJoinColumns = [JoinColumn(name = "skill_id")]
    )
    var skills: MutableSet<SkillEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "internship", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var missions: MutableList<InternshipMissionEntity> = mutableListOf()
)
