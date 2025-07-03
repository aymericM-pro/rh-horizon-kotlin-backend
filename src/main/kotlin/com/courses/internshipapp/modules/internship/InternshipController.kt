package com.courses.internshipapp.modules.internship

import com.courses.internshipapp.modules.internship.dtos.InternshipCreate
import com.courses.internshipapp.modules.internship.dtos.InternshipResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/internships")
class InternshipController(
    private val internshipService: InternshipService
) {

    @PostMapping("/student/{studentId}")
    fun create(@PathVariable studentId: UUID, @RequestBody request: InternshipCreate): ResponseEntity<InternshipResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(internshipService.createInternship(studentId, request))

    @PatchMapping("/{internshipId}/validate/{adminId}")
    fun validate(@PathVariable internshipId: UUID, @PathVariable adminId: UUID): ResponseEntity<InternshipResponse> =
        ResponseEntity.ok(internshipService.validateInternship(internshipId, adminId))

    @PatchMapping("/{internshipId}/rejected/{adminId}")
    fun rejected(@PathVariable internshipId: UUID, @PathVariable adminId: UUID): ResponseEntity<InternshipResponse> =
        ResponseEntity.ok(internshipService.rejectedInternship(internshipId, adminId))

    @GetMapping
    fun getAll(): ResponseEntity<List<InternshipResponse>> =
        ResponseEntity.ok(internshipService.getAll())

    @GetMapping("/me")
    fun getMyInternships(@AuthenticationPrincipal user: UserDetails): ResponseEntity<List<InternshipResponse>> =
        ResponseEntity.ok(internshipService.getMyInternships(user))

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<InternshipResponse> =
        ResponseEntity.ok(internshipService.getById(id))

    @DeleteMapping
    fun deleteAll(): ResponseEntity<Void> {
        internshipService.deleteAll()
        return ResponseEntity.noContent().build()
    }
}
