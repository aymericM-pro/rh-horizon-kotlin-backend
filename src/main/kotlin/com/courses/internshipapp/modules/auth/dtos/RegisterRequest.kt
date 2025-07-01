package com.courses.internshipapp.modules.auth.dtos

import com.courses.internshipapp.modules.roles.Role

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: Role
)
