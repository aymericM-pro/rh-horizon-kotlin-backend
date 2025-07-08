package com.courses.internshipapp.modules.auth.dtos

data class LoginRequest(
    val email: String,
    val password: String
)
