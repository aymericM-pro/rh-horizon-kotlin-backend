package com.courses.internshipapp.modules.internship.enums

enum class ErrorInternship(val code: String, val message: String) {
    NOT_FOUND("I001", "Internship not found"),
    INVALID_DATA("I002", "Invalid internship data")
}
