package com.courses.internshipapp.core.errors

import com.courses.internshipapp.modules.internship.enums.ErrorInternship
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException): ResponseEntity<Map<String, String>> {
        val error = ex.error
        val (code, message) = when (error) {
            is ErrorInternship -> error.code to error.message
            else -> "UNKNOWN" to "An unknown error occurred"
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(mapOf("code" to code, "message" to message))
    }
}
