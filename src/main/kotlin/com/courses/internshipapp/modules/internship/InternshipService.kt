package com.courses.internshipapp.modules.internship

import com.courses.internshipapp.modules.admin.AdminRepository
import com.courses.internshipapp.modules.internship.dtos.InternshipCardDto
import com.courses.internshipapp.modules.internship.dtos.InternshipCreate
import com.courses.internshipapp.modules.internship.dtos.InternshipResponse
import com.courses.internshipapp.modules.internship.entities.CompanyDetails
 import com.courses.internshipapp.modules.skills.SkillRepository
import com.courses.internshipapp.modules.students.StudentEntity
import com.courses.internshipapp.modules.students.StudentRepository
import com.courses.internshipapp.modules.users.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class InternshipService(
    private val internshipRepository: InternshipRepository,
    private val studentRepository: StudentRepository,
    private val adminRepository: AdminRepository,
    private val userRepository: UserRepository,
    private val skillRepository: SkillRepository
) {

    fun createInternship(studentId: UUID, request: InternshipCreate): InternshipResponse {
        val student = studentRepository.findById(studentId)
            .orElseThrow { RuntimeException("Étudiant introuvable avec l'ID: $studentId") }

        val skillEntities = request.skills.map { skillName ->
            skillRepository.findByName(skillName)
                ?: throw RuntimeException("Compétence introuvable: $skillName")
        }.toMutableSet()

        val entity = InternshipMapper.toEntity(request, student, skillEntities)
        entity.status = InternshipStatus.SUBMITTED;
        val saved = internshipRepository.save(entity)
        return InternshipMapper.toResponse(saved)
    }

    fun validateInternship(internshipId: UUID, adminId: UUID): InternshipResponse {
        val internship = internshipRepository.findById(internshipId)
            .orElseThrow { RuntimeException("Stage introuvable avec l'ID: $internshipId") }

        val admin = adminRepository.findById(adminId)
            .orElseThrow { RuntimeException("Administrateur introuvable avec l'ID: $adminId") }

        internship.validated = true
        internship.status = InternshipStatus.APPROVED
        internship.validatedBy = admin

        val saved = internshipRepository.save(internship)
        return InternshipMapper.toResponse(saved)
    }

    fun rejectedInternship(internshipId: UUID, adminId: UUID): InternshipResponse {
        val internship = internshipRepository.findById(internshipId)
            .orElseThrow { RuntimeException("Stage introuvable avec l'ID: $internshipId") }

        val admin = adminRepository.findById(adminId)
            .orElseThrow { RuntimeException("Administrateur introuvable avec l'ID: $adminId") }

        internship.validated = false
        internship.status = InternshipStatus.REJECTED
        internship.validatedBy = admin

        val saved = internshipRepository.save(internship)
        return InternshipMapper.toResponse(saved)
    }

    fun getAll(): List<InternshipResponse> =
        internshipRepository.findAll().map { InternshipMapper.toResponse(it) }

    fun getAllInternshipStudent(studentId: UUID): List<InternshipCardDto> {
        val projections = internshipRepository.findAllCardByStudentId(studentId)

        return projections
            .groupBy { it.internshipId }
            .map { (internshipId, group) ->
                val first = group.first()

                val company = CompanyDetails(
                    name = first.company_name ?: "",
                    sector = first.company_sector ?: "",
                    size = first.company_size ?: "",
                    website = first.company_website ?: "",
                    address = first.company_address ?: "",
                    city = first.company_city ?: "",
                    postalCode = first.company_postalCode ?: ""
                )

                val skills = group.mapNotNull { it.skill_name }.distinct()

                InternshipCardDto(
                    internshipId = internshipId,
                    title = first.title ?: "",
                    description = first.description ?: "",
                    status = first.status ?: "",
                    company = company,
                    skills = skills
                )
            }
    }

    fun getMyInternships(currentUser: UserDetails): List<InternshipResponse> {
        val student = userRepository.findByEmail(currentUser.username) as? StudentEntity
            ?: throw RuntimeException("Seuls les étudiants peuvent voir leurs stages")

        return internshipRepository.findByCreatedBy(student).map { InternshipMapper.toResponse(it) }
    }

    fun getById(id: UUID): InternshipResponse {
        val internship = internshipRepository.findById(id)
            .orElseThrow { RuntimeException("Stage introuvable avec l'ID: $id") }

        return InternshipMapper.toResponse(internship)
    }

    fun deleteAll() {
        internshipRepository.deleteAll()
    }
}
