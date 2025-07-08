package com.courses.internshipapp.modules.internship

import com.courses.internshipapp.modules.internship.dtos.InternshipCardDto
import com.courses.internshipapp.modules.internship.dtos.InternshipCreate
import com.courses.internshipapp.modules.internship.dtos.InternshipResponse
import com.courses.internshipapp.modules.internship.dtos.SubmitRequest
import org.slf4j.LoggerFactory
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
    private val logger = LoggerFactory.getLogger(InternshipController::class.java)

    @PostMapping("/student/{studentId}")
    fun create(
        @PathVariable studentId: UUID,
        @RequestBody request: InternshipCreate
    ): ResponseEntity<InternshipResponse> {
        logger.info("Requête de création reçue pour studentId: {}", studentId)
        logger.info("Données reçues: {}", request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(internshipService.createInternship(studentId, request))
    }

    @PatchMapping("/{internshipId}/submit")
    fun submitInternship(
        @PathVariable internshipId: UUID,
        @RequestBody request: SubmitRequest
    ): ResponseEntity<InternshipResponse> {
        logger.info("Requête de soumission du stage $internshipId par l'étudiant ${request.studentId}")
        return ResponseEntity.ok(internshipService.submitInternship(internshipId, request.studentId))
    }

    @PatchMapping("/{internshipId}/approuve/{adminId}")
    fun approuve(@PathVariable internshipId: UUID, @PathVariable adminId: UUID): ResponseEntity<InternshipResponse> =
        ResponseEntity.ok(internshipService.approuvedInternship(internshipId, adminId))

    @PatchMapping(("/{internshipId}/validate/{adminId}"))
    fun validate(@PathVariable internshipId: UUID, @PathVariable adminId: UUID): ResponseEntity<InternshipResponse> =
        ResponseEntity.ok(internshipService.validatedInternship(internshipId, adminId))

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

    @GetMapping("/student/{studentId}/cards")
    fun getStudentInternshipCards(@PathVariable studentId: UUID): ResponseEntity<List<InternshipCardDto>> {
        val cards = internshipService.getAllInternshipStudent(studentId)
        return ResponseEntity.ok(cards)
    }
}
