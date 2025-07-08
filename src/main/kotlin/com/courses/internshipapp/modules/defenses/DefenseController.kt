package com.courses.internshipapp.modules.defenses

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/defenses")
class DefenseController(
    private val defenseService: DefenseService
) {

    @PostMapping("/{internshipId}")
    fun createDefense(
        @PathVariable internshipId: UUID,
        @RequestBody request: DefenseCreate
    ): ResponseEntity<DefenseResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(defenseService.create(internshipId, request))

    @GetMapping
    fun getAll(): ResponseEntity<List<DefenseResponse>> =
        ResponseEntity.ok(defenseService.getAll())

    @GetMapping("/{defenseId}")
    fun getById(@PathVariable defenseId: UUID): ResponseEntity<DefenseResponse> =
        ResponseEntity.ok(defenseService.getById(defenseId))

    @DeleteMapping("/{defenseId}")
    fun delete(@PathVariable defenseId: UUID): ResponseEntity<Void> {
        defenseService.delete(defenseId)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{defenseId}/validate")
    @PreAuthorize("hasRole('ADMIN')")
    fun validate(@PathVariable defenseId: UUID): ResponseEntity<DefenseResponse> =
        ResponseEntity.ok(defenseService.scheduledDefense(defenseId))

    @PatchMapping("/{defenseId}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    fun complete(@PathVariable defenseId: UUID): ResponseEntity<DefenseResponse> =
        ResponseEntity.ok(defenseService.complete(defenseId))

    @PatchMapping("/{defenseId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    fun reject(@PathVariable defenseId: UUID): ResponseEntity<DefenseResponse> =
        ResponseEntity.ok(defenseService.reject(defenseId))
}
