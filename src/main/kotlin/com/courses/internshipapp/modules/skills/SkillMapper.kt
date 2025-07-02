package com.courses.internshipapp.modules.skills

object SkillMapper {
    fun toResponse(entity: SkillEntity): SkillResponse =
        SkillResponse(
            id = entity.id,
            name = entity.name
        )

    fun toEntity(request: SkillRequest): SkillEntity =
        SkillEntity(
            name = request.name
        )
}