package com.courses.internshipapp.modules.auth

import com.courses.internshipapp.core.errors.CustomUserDetails
import com.courses.internshipapp.core.security.JwtService
import com.courses.internshipapp.core.security.UserDetailsServiceImpl
import com.courses.internshipapp.modules.auth.dtos.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) {
    fun login(request: LoginRequest): TokenResponse {
        val auth = UsernamePasswordAuthenticationToken(request.email, request.password)
        authenticationManager.authenticate(auth)

        val userDetails = userDetailsServiceImpl.loadUserByUsername(request.email)
        val jwt = jwtService.generateToken(userDetails)

        return TokenResponse(token = jwt)
    }

    fun register(request: RegisterRequest): TokenResponse {
        val user = userDetailsServiceImpl.createUser(request)
        val jwt = jwtService.generateToken(CustomUserDetails(user))

        return TokenResponse(token = jwt)
    }
}
