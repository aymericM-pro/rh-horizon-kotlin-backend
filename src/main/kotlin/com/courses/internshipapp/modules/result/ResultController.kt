package com.courses.internshipapp.modules.result

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/results")
class ResultController(
    private val resultService: ResultService
) {
    @PostMapping("/{defenseId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun createResult(
        @PathVariable defenseId: UUID,
        @RequestBody request: ResultCreate
    ): ResponseEntity<ResultResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(resultService.create(defenseId, request))

    @GetMapping("/{resultId}")
    fun getById(@PathVariable resultId: UUID): ResponseEntity<ResultResponse> =
        ResponseEntity.ok(resultService.getById(resultId))

    @GetMapping("/defense/{defenseId}")
    fun getByDefenseId(@PathVariable defenseId: UUID): ResponseEntity<ResultResponse> =
        ResponseEntity.ok(resultService.getByDefenseId(defenseId))

    @DeleteMapping("/{resultId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable resultId: UUID): ResponseEntity<Void> {
        resultService.delete(resultId)
        return ResponseEntity.noContent().build()
    }
}
