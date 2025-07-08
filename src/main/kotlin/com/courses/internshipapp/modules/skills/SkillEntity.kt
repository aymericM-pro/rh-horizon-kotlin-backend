package com.courses.internshipapp.modules.skills

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "skills")
class SkillEntity (

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, unique = true)
    var id: UUID? = null,

    @Column(nullable = false, unique = true)
    var name: String
)
