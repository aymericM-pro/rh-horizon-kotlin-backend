package com.courses.internshipapp.modules.users

import com.courses.internshipapp.modules.users.dtos.UserCreate
import com.courses.internshipapp.modules.users.dtos.UserResponse

import com.courses.internshipapp.modules.admin.AdminEntity
import com.courses.internshipapp.modules.roles.Role
import com.courses.internshipapp.modules.students.StudentEntity

object UserMapper {

     fun toEntity(request: UserCreate): UserEntity {
        return when (request.role) {
            Role.STUDENT -> StudentEntity(
                name = request.name,
                email = request.email,
                password = request.password
            )
            Role.ADMIN -> AdminEntity(
                name = request.name,
                email = request.email,
                password = request.password
            )
            else -> throw IllegalArgumentException("Unsupported role: ${request.role}")
        }
    }

    fun toResponse(user: UserEntity): UserResponse {
        return UserResponse(
            userId = user.userId!!,
            name = user.name,
            email = user.email,
            role = user.role
        )
    }
}
