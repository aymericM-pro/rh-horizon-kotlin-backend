package com.courses.internshipapp.modules.users

import com.courses.internshipapp.core.errors.BusinessException
import com.courses.internshipapp.modules.users.dtos.UserCreate
import com.courses.internshipapp.modules.users.dtos.UserResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(request: UserCreate): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw BusinessException(ErrorUser.EMAIL_ALREADY_EXISTS);
        }

        val entity = UserMapper.toEntity(request)
        val saved = userRepository.save(entity)
        return UserMapper.toResponse(saved)
    }

    fun getAllUsers(): List<UserResponse> {
        val users = userRepository.findAll()

        if (users.isEmpty()) {
            throw BusinessException(ErrorUser.USER_NOT_FOUND)
        }

        return users.map { UserMapper.toResponse(it) }
    }

    fun getUserById(id: UUID): UserResponse =
        userRepository.findById(id)
            .map { UserMapper.toResponse(it) }
            .orElseThrow { BusinessException(ErrorUser.USER_NOT_FOUND) }

    fun deleteUser(id: UUID) {
        userRepository.deleteById(id)
    }
}
