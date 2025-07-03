package com.courses.internshipapp.modules.result

import java.math.BigDecimal
import java.util.UUID

data class ResultResponse(
    val id: UUID,
    val grade: BigDecimal,
    val appreciation: String?
)