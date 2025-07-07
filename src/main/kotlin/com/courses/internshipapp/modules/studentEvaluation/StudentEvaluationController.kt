package com.courses.internshipapp.modules.studentEvaluation

import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("/api/evaluations")
class StudentEvaluationController(
    private val service: StudentEvaluationService
) {

    @PostMapping
    fun create(
        @RequestBody request: StudentEvaluationCreateRequest
    ): StudentEvaluationResponse =
        service.create(request)

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: UUID
    ): StudentEvaluationResponse =
        service.getById(id)

    @GetMapping
    fun getAll(): List<StudentEvaluationResponse> =
        service.getAll()

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody request: StudentEvaluationCreateRequest
    ): StudentEvaluationResponse =
        service.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: UUID
    ) {
        service.delete(id)
    }
}