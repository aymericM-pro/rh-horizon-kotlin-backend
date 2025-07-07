package com.courses.internshipapp.modules.evaluationCriteria

import com.courses.internshipapp.core.errors.BusinessException
import com.courses.internshipapp.modules.studentEvaluation.StudentEvaluationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class EvaluationCriteriaService(
    private val repository: EvaluationCriteriaRepository,
    private val evaluationRepository: StudentEvaluationRepository
) {

    @Transactional
    fun create(evaluationId: UUID, request: EvaluationCriteriaCreateRequest): EvaluationCriteriaResponse {
        val evaluation = evaluationRepository.findById(evaluationId)
            .orElseThrow { BusinessException(ErrorEvaluationCriteria.INVALID_INPUT) }

        val entity = EvaluationCriteriaMapper.toEntity(request, evaluation)
        val saved = repository.save(entity)
        return EvaluationCriteriaMapper.toResponse(saved)
    }

    fun getById(id: UUID): EvaluationCriteriaResponse {
        val entity = repository.findById(id)
            .orElseThrow { BusinessException(ErrorEvaluationCriteria.NOT_FOUND) }

        return EvaluationCriteriaMapper.toResponse(entity)
    }

    fun getAll(): List<EvaluationCriteriaResponse> {
        return repository.findAll().map(EvaluationCriteriaMapper::toResponse)
    }

    @Transactional
    fun update(id: UUID, request: EvaluationCriteriaCreateRequest): EvaluationCriteriaResponse {
        val existing = repository.findById(id)
            .orElseThrow { BusinessException(ErrorEvaluationCriteria.NOT_FOUND) }

        existing.label = request.label
        existing.grades = request.grades

        val updated = repository.save(existing)
        return EvaluationCriteriaMapper.toResponse(updated)
    }

    @Transactional
    fun delete(id: UUID) {
        val existing = repository.findById(id)
            .orElseThrow { BusinessException(ErrorEvaluationCriteria.NOT_FOUND) }
        repository.delete(existing)
    }
}
