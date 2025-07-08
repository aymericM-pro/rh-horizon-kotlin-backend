package com.courses.internshipapp.modules.internship

import com.courses.internshipapp.core.errors.BusinessException
import com.courses.internshipapp.modules.admin.AdminRepository
import com.courses.internshipapp.modules.internship.dtos.InternshipCardDto
import com.courses.internshipapp.modules.internship.dtos.InternshipCreate
import com.courses.internshipapp.modules.internship.dtos.InternshipResponse
import com.courses.internshipapp.modules.internship.entities.CompanyDetails
import com.courses.internshipapp.modules.internship.enums.ErrorInternship
import com.courses.internshipapp.modules.skills.SkillRepository
import com.courses.internshipapp.modules.students.StudentEntity
import com.courses.internshipapp.modules.students.StudentRepository
import com.courses.internshipapp.modules.users.UserRepository
import org.slf4j.LoggerFactory
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
    private val logger = LoggerFactory.getLogger(InternshipService::class.java)

    fun submitInternship(internshipId: UUID, studentId: UUID): InternshipResponse {
        logger.info("Tentative de soumission du stage $internshipId par l'étudiant $studentId")

        val internship = internshipRepository.findById(internshipId)
            .orElseThrow {
                logger.warn("Stage non trouvé : $internshipId")
                BusinessException(ErrorInternship.NOT_FOUND)
            }

        if (internship.createdBy.userId != studentId) {
            logger.warn("Etudiant non autorisé à soumettre ce stage. Étudiant attendu : ${internship.createdBy.userId}, reçu : $studentId")
            throw BusinessException(ErrorInternship.CANT_SUBMIT)
        }

        val validStatusesForSubmission = setOf(
            InternshipStatus.DRAFT,
            InternshipStatus.REJECTED,
            InternshipStatus.CREATED
        )

        if (internship.status !in validStatusesForSubmission) {
            logger.warn("⚠ Statut invalide pour soumission : ${internship.status}")
            throw BusinessException(ErrorInternship.NEED_SUBMIT)
        }

        internship.status = InternshipStatus.SUBMITTED
        internshipRepository.save(internship)

        logger.info("Stage $internshipId soumis avec succès par l'étudiant $studentId")

        return InternshipMapper.toResponse(internship)
    }

    fun createInternship(studentId: UUID, request: InternshipCreate): InternshipResponse {
        logger.info("Création d’un stage pour l’étudiant avec ID: {}", studentId)
        logger.info("Contenu de la requête: {}", request)

        val student = studentRepository.findById(studentId)
            .orElseThrow {
                logger.error("Étudiant introuvable avec l'ID: {}", studentId)
                BusinessException(ErrorInternship.NOT_FOUND)
            }

        val skillEntities = request.skills.map { skillName ->
            skillRepository.findByName(skillName)
                ?: run {
                    logger.error("Compétence introuvable: {}", skillName)
                    throw BusinessException(ErrorInternship.INVALID_DATA)
                }
        }.toMutableSet()

        val entity = InternshipMapper.toEntity(request, student, skillEntities)
        entity.status = InternshipStatus.CREATED

        logger.info("Entité à sauvegarder: {}", entity)

        val saved = internshipRepository.save(entity)

        logger.info("Stage sauvegardé avec ID: {}", saved.internshipId)

        return InternshipMapper.toResponse(saved)
    }

    fun approuveInternship(internshipId: UUID, currentUser: UserDetails): InternshipResponse {
        val internship = internshipRepository.findById(internshipId)
            .orElseThrow {
                logger.error("Stage introuvable avec l’ID: {}", internshipId)
                BusinessException(ErrorInternship.NOT_FOUND)
            }

/*        val admin = adminRepository.findById(currentUser)
            .orElseThrow {
                logger.error("Administrateur introuvable avec l’ID: {}", adminId)
                BusinessException(ErrorInternship.INVALID_DATA)
            }*/

        internship.validated = true
        internship.status = InternshipStatus.APPROVED
        /*internship.validatedBy = admin*/

        val saved = internshipRepository.save(internship)
        logger.info("Stage validé: {}", saved.internshipId)

        return InternshipMapper.toResponse(saved)
    }

    fun validatedInternship(internshipId: UUID, currentUser: UserDetails): InternshipResponse {
        val internship = internshipRepository.findById(internshipId)
            .orElseThrow {
                logger.error("Stage introuvable avec l’ID: {}", internshipId)
                BusinessException(ErrorInternship.NOT_FOUND)
            }

/*
        val admin = adminRepository.findById(adminId)
            .orElseThrow {
                logger.error("Administrateur introuvable avec l’ID: {}", adminId)
                BusinessException(ErrorInternship.INVALID_DATA)
            }
*/

        internship.validated = true
        internship.status = InternshipStatus.VALIDATED
/*
        internship.validatedBy = admin
*/

        val saved = internshipRepository.save(internship)
        logger.info("Stage validé: {}", saved.internshipId)

        return InternshipMapper.toResponse(saved)
    }

    fun rejectedInternship(internshipId: UUID, currentUser: UserDetails): InternshipResponse {
        val internship = internshipRepository.findById(internshipId)
            .orElseThrow {
                logger.error("Stage introuvable avec l’ID: {}", internshipId)
                BusinessException(ErrorInternship.NOT_FOUND)
            }

 /*       val admin = adminRepository.findById(adminId)
            .orElseThrow {
                logger.error("Administrateur introuvable avec l’ID: {}", adminId)
                BusinessException(ErrorInternship.INVALID_DATA)
            }
*/
        internship.validated = false
        internship.status = InternshipStatus.REJECTED

/*
        internship.validatedBy = admin
*/

        val saved = internshipRepository.save(internship)
        logger.info("Stage rejeté: {}", saved.internshipId)

        return InternshipMapper.toResponse(saved)
    }

    fun getAll(): List<InternshipResponse> {
        logger.info("Récupération de tous les stages")
        return internshipRepository.findAll().map { InternshipMapper.toResponse(it) }
    }

    fun getAllInternshipStudent(studentId: UUID): List<InternshipCardDto> {
        logger.info("Récupération des stages pour l’étudiant: {}", studentId)

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
            ?: run {
                logger.error("Utilisateur non étudiant: {}", currentUser.username)
                throw BusinessException(ErrorInternship.INVALID_DATA)
            }

        logger.info("Récupération des stages pour {}", currentUser.username)

        return internshipRepository.findByCreatedBy(student).map {
            InternshipMapper.toResponse(it)
        }
    }

    fun getById(id: UUID): InternshipResponse {
        logger.info("Récupération du stage {}", id)

        val internship = internshipRepository.findById(id)
            .orElseThrow {
                logger.error("Stage introuvable avec l’ID: {}", id)
                BusinessException(ErrorInternship.NOT_FOUND)
            }

        return InternshipMapper.toResponse(internship)
    }

    fun deleteAll() {
        logger.warn("🗑️ Suppression de tous les stages")
        internshipRepository.deleteAll()
    }
}
