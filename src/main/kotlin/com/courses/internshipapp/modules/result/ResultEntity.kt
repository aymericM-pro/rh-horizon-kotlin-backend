package com.courses.internshipapp.modules.result

import com.courses.internshipapp.modules.defenses.DefenseEntity
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "defense_results")
data class ResultEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val resultId: UUID? = null,

    @Column(nullable = false, precision = 4, scale = 2) // max 20.00
    var grade: BigDecimal,

    @Column(length = 1000)
    var appreciation: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defense_id", nullable = false, unique = true)
    var defense: DefenseEntity
)
