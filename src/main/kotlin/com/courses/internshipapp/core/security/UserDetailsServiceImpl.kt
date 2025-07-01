package com.courses.internshipapp.core.security

import com.courses.internshipapp.core.errors.CustomUserDetails
import com.courses.internshipapp.modules.admin.AdminEntity
import com.courses.internshipapp.modules.auth.dtos.RegisterRequest
import com.courses.internshipapp.modules.roles.Role
import com.courses.internshipapp.modules.students.StudentEntity
import com.courses.internshipapp.modules.users.UserEntity
import com.courses.internshipapp.modules.users.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("Utilisateur non trouvé")
        return CustomUserDetails(user)
    }

    fun createUser(request: RegisterRequest): UserEntity {
        if (userRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("L'email est déjà utilisé")
        }

        val encodedPassword = passwordEncoder.encode(request.password)

        val user = when (request.role) {
            Role.STUDENT -> StudentEntity(
                name = request.name,
                email = request.email,
                password = encodedPassword
            )
            Role.ADMIN -> AdminEntity(
                name = request.name,
                email = request.email,
                password = encodedPassword
            )
            Role.USER -> throw IllegalArgumentException("Le rôle USER ne peut pas s'enregistrer directement")
        }

        return userRepository.save(user)
    }

}
