package com.courses.internshipapp.modules.evaluationCriteria

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/criteria")
class EvaluationCriteriaController(
    private val service: EvaluationCriteriaService
) {

    @PostMapping("/evaluation/{evaluationId}")
    fun create(
        @PathVariable evaluationId: UUID,
        @RequestBody request: EvaluationCriteriaCreateRequest
    ): EvaluationCriteriaResponse =
        service.create(evaluationId, request)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): EvaluationCriteriaResponse =
        service.getById(id)

    @GetMapping
    fun getAll(): List<EvaluationCriteriaResponse> =
        service.getAll()

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody request: EvaluationCriteriaCreateRequest
    ): EvaluationCriteriaResponse =
        service.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        service.delete(id)
    }
}
