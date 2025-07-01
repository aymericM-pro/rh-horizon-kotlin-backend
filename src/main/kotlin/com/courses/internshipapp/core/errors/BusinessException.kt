package com.courses.internshipapp.core.errors

class BusinessException(val error: Enum<*>): RuntimeException() {
}