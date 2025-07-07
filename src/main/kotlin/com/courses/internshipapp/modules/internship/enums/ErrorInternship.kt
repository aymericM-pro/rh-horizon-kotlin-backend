package com.courses.internshipapp.modules.internship.enums

enum class ErrorInternship(val code: String, val message: String) {
    NOT_FOUND("I001", "Internship not found"),
    INVALID_DATA("I002", "Invalid internship data"),
    CANT_SUBMIT("I003", "Vous ne pouvez pas soumettre ce stage"),
    NEED_SUBMIT("","Seuls les stages en brouillon ou rejetés peuvent être soumis.");
}
