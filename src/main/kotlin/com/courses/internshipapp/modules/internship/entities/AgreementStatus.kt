package com.courses.internshipapp.modules.internship.entities

import jakarta.persistence.Embeddable

@Embeddable
class AgreementStatus(
    var dataProcessing: Boolean = false,
    var terms: Boolean = false,
    var truthfulness: Boolean = false
)