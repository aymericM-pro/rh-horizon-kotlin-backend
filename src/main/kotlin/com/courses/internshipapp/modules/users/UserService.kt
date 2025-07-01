package com.courses.internshipapp.modules.users

import com.courses.internshipapp.modules.users.dtos.UserCreate
import com.courses.internshipapp.modules.users.dtos.UserResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(request: UserCreate): UserResponse {
        val entity = UserMapper.toEntity(request)
        val saved = userRepository.save(entity)
        return UserMapper.toResponse(saved)
    }

    fun getAllUsers(): List<UserResponse> =
        userRepository.findAll().map { UserMapper.toResponse(it) }

    fun getUserById(id: UUID): UserResponse =
        userRepository.findById(id)
            .map { UserMapper.toResponse(it) }
            .orElseThrow { RuntimeException("User not found") }

    fun deleteUser(id: UUID) {
        userRepository.deleteById(id)
    }
}
