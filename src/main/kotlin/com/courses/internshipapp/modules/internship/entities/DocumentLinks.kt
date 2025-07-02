package com.courses.internshipapp.modules.internship.entities

import jakarta.persistence.Embeddable

@Embeddable
class DocumentLinks(
    var cvUrl: String,
    var coverLetterUrl: String,
    var transcriptUrl: String,
    var agreementUrl: String
)