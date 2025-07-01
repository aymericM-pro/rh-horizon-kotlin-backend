package com.courses.internshipapp.modules.users.dtos

import com.courses.internshipapp.modules.roles.Role

data class UserCreate(
    val name: String,
    val email: String,
    val password: String,
    val role: Role = Role.STUDENT
)
