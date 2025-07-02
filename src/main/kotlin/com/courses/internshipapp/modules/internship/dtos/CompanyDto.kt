package com.courses.internshipapp.modules.internship.dtos

data class CompanyDto(
    val name: String,
    val sector: String,
    val size: String,
    val website: String,
    val address: String,
    val city: String,
    val postalCode: String
)