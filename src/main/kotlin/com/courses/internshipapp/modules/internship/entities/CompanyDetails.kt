package com.courses.internshipapp.modules.internship.entities

import jakarta.persistence.Embeddable

@Embeddable
class CompanyDetails(
    var name: String,
    var sector: String,
    var size: String,
    var website: String,
    var address: String,
    var city: String,
    var postalCode: String
)