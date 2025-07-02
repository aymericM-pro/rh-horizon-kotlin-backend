package com.courses.internshipapp.modules.internship.entities

import jakarta.persistence.Embeddable

@Embeddable
class SupervisorDetails(
    var firstName: String,
    var lastName: String,
    var position: String,
    var department: String,
    var email: String,
    var phone: String
)