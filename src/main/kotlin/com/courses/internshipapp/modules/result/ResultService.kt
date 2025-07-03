package com.courses.internshipapp.modules.result

import com.courses.internshipapp.modules.defenses.DefenseRepository
import com.courses.internshipapp.modules.defenses.DefenseStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*


@Service
class ResultService(
    private val resultRepository: ResultRepository,
    private val defenseRepository: DefenseRepository
) {

    @Transactional
    fun create(defenseId: UUID, request: ResultCreate): ResultResponse {
        val defense = defenseRepository.findById(defenseId)
            .orElseThrow { NoSuchElementException("Defense not found") }

        if (resultRepository.existsByDefenseDefenseId(defenseId)) {
            throw IllegalStateException("A result already exists for this defense.")
        }

        val result = ResultEntity(
            grade = request.grade,
            appreciation = request.appreciation,
            defense = defense
        )

        defense.status = if (request.grade.compareTo(BigDecimal("10.00")) >= 0)
            DefenseStatus.SUCCESSFUL
        else
            DefenseStatus.FAILED


        val saved = resultRepository.save(result)
        return ResultMapper.toResponse(saved)
    }

    fun getById(resultId: UUID): ResultResponse {
        val result = resultRepository.findById(resultId)
            .orElseThrow { NoSuchElementException("Result not found") }
        return ResultMapper.toResponse(result)
    }

    fun getByDefenseId(defenseId: UUID): ResultResponse {
        val result = resultRepository.findByDefenseDefenseId(defenseId)
            ?: throw NoSuchElementException("No result found for this defense.")
        return ResultMapper.toResponse(result)
    }

    @Transactional
    fun delete(resultId: UUID) {
        val result = resultRepository.findById(resultId)
            .orElseThrow { NoSuchElementException("Result not found") }
        resultRepository.delete(result)
    }
}

