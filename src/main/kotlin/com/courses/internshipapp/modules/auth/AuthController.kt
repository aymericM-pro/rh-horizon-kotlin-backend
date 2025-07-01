package com.courses.internshipapp.modules.auth

import com.courses.internshipapp.modules.auth.dtos.LoginRequest
import com.courses.internshipapp.modules.auth.dtos.RegisterRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest) =
        ResponseEntity.ok(authService.login(request))

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest) =
        ResponseEntity.ok(authService.register(request))
}
