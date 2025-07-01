package com.courses.internshipapp.modules.users.dtos

import com.courses.internshipapp.modules.roles.Role
import java.util.UUID

data class UserResponse(
    val userId: UUID,
    val name: String,
    val email: String,
    val role: Role
)
