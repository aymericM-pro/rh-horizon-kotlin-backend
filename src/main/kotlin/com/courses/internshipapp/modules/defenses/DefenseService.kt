package com.courses.internshipapp.modules.defenses

import com.courses.internshipapp.modules.internship.InternshipRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefenseService(
    private val defenseRepository: DefenseRepository,
    private val internshipRepository: InternshipRepository
) {

    fun create(internshipId: UUID, dto: DefenseCreate): DefenseResponse {
        val internship = internshipRepository.findById(internshipId)
            .orElseThrow { IllegalArgumentException("Internship not found") }

        if (defenseRepository.findByInternship_InternshipId(internshipId) != null) {
            throw IllegalStateException("Defense already exists for this internship.")
        }

        val defense = DefenseMapper.toEntity(dto, internship)
        return DefenseMapper.toResponse(defenseRepository.save(defense))
    }

    fun getAll(): List<DefenseResponse> =
        defenseRepository.findAll().map(DefenseMapper::toResponse)

    fun getById(defenseId: UUID): DefenseResponse =
        defenseRepository.findById(defenseId)
            .map(DefenseMapper::toResponse)
            .orElseThrow { NoSuchElementException("Defense not found") }

    fun delete(defenseId: UUID) {
        if (!defenseRepository.existsById(defenseId)) {
            throw NoSuchElementException("Defense not found")
        }
        defenseRepository.deleteById(defenseId)
    }

    fun scheduledDefense(defenseId: UUID): DefenseResponse {
        val defense = getDefenseOrThrow(defenseId)

        if (defense.status != DefenseStatus.PENDING_REQUEST) {
            throw IllegalStateException("Only pending requests can be validated. Current status: ${defense.status}")
        }

        defense.status = DefenseStatus.SCHEDULED
        return DefenseMapper.toResponse(defenseRepository.save(defense))
    }

    fun complete(defenseId: UUID): DefenseResponse {
        val defense = getDefenseOrThrow(defenseId)

        if (defense.status != DefenseStatus.SCHEDULED) {
            throw IllegalStateException("Only scheduled defenses can be completed. Current status: ${defense.status}")
        }

        defense.status = DefenseStatus.SUCCESSFUL
        return DefenseMapper.toResponse(defenseRepository.save(defense))
    }

    fun reject(defenseId: UUID): DefenseResponse {
        val defense = getDefenseOrThrow(defenseId)

        when (defense.status) {
            DefenseStatus.SUCCESSFUL -> throw IllegalStateException("Cannot reject a completed defense.")
            DefenseStatus.FAILED -> throw IllegalStateException("This defense is already rejected.")
            else -> {
                defense.status = DefenseStatus.FAILED
            }
        }

        return DefenseMapper.toResponse(defenseRepository.save(defense))
    }


    private fun getDefenseOrThrow(id: UUID): DefenseEntity =
        defenseRepository.findById(id).orElseThrow { NoSuchElementException("Defense not found") }
}
