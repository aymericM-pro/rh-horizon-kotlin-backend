package com.courses.internshipapp.modules.studentEvaluation

import com.courses.internshipapp.core.errors.BusinessException
import com.courses.internshipapp.modules.evaluationCriteria.EvaluationCriteriaEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StudentEvaluationService(
    private val repository: StudentEvaluationRepository
) {

    @Transactional
    fun create(request: StudentEvaluationCreateRequest): StudentEvaluationResponse {
        val entity = StudentEvaluationMapper.toEntity(request)
        val saved = repository.save(entity)
        return StudentEvaluationMapper.toResponse(saved)
    }

    fun getById(id: UUID): StudentEvaluationResponse {
        val entity = repository.findById(id)
            .orElseThrow { BusinessException(ErrorStudentEvaluation.NOT_FOUND) }

        return StudentEvaluationMapper.toResponse(entity)
    }

    fun getAll(): List<StudentEvaluationResponse> {
        return repository.findAll().map(StudentEvaluationMapper::toResponse)
    }

    @Transactional
    fun update(id: UUID, request: StudentEvaluationCreateRequest): StudentEvaluationResponse {
        val existing = repository.findById(id)
            .orElseThrow { BusinessException(ErrorStudentEvaluation.NOT_FOUND) }

        existing.date = request.date
        existing.comments = request.comments

        existing.criteria.clear()
        existing.criteria.addAll(
            request.criteria.map {
                EvaluationCriteriaEntity(
                    label = it.label,
                    grades = it.grades,
                    Evaluation = existing
                )
            }
        )

        val saved = repository.save(existing)
        return StudentEvaluationMapper.toResponse(saved)
    }

    @Transactional
    fun delete(id: UUID) {
        val entity = repository.findById(id)
            .orElseThrow { BusinessException(ErrorStudentEvaluation.NOT_FOUND) }

        repository.delete(entity)
    }
}
