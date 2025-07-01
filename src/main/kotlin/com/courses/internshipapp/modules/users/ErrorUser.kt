package com.courses.internshipapp.modules.users

enum class ErrorUser(val code: String, val message: String) {
    USER_NOT_FOUND("USER_NOT_FOUND", "Utilisateur non trouvé"),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "L'email est déjà pris"),
}
