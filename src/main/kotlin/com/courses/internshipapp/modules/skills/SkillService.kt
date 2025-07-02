package com.courses.internshipapp.modules.skills

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SkillService(
    private val skillRepository: SkillRepository,
) {

    fun getAll(): List<SkillResponse> =
        skillRepository.findAll().map { SkillMapper.toResponse(it) }

    fun getById(id: UUID): SkillResponse =
        SkillMapper.toResponse(
            skillRepository.findById(id).orElseThrow { RuntimeException("Skill not found") }
        )

    fun create(request: SkillRequest): SkillResponse {
        val entity = skillRepository.save(SkillMapper.toEntity(request))
        return SkillMapper.toResponse(entity)
    }

    fun createMany(requests: List<SkillRequest>): List<SkillResponse> {
        val entities = requests.map { SkillMapper.toEntity(it) }
        return skillRepository.saveAll(entities).map(SkillMapper::toResponse)
    }
}
