package com.courses.internshipapp.modules.skills

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/skills")
class SkillController(
    private val skillService: SkillService
) {

    @GetMapping
    fun getAll(): ResponseEntity<List<SkillResponse>> =
        ResponseEntity.ok(skillService.getAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<SkillResponse> =
        ResponseEntity.ok(skillService.getById(id))

    @PostMapping
    fun create(@RequestBody request: SkillRequest): ResponseEntity<SkillResponse> =
        ResponseEntity.ok(skillService.create(request))

    @PostMapping("/bulk")
    fun createBulk(@RequestBody requests: List<SkillRequest>): ResponseEntity<List<SkillResponse>> =
        ResponseEntity.ok(skillService.createMany(requests))
}
