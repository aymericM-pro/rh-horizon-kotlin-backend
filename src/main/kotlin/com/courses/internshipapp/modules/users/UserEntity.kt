package com.courses.internshipapp.modules.users

import com.courses.internshipapp.modules.roles.Role
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
abstract class UserEntity(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role,

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, unique = true)
    var userId: UUID? = null
)