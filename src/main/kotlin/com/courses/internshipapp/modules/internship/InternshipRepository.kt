package com.courses.internshipapp.modules.internship

import com.courses.internshipapp.modules.internship.projections.InternshipCardWithSkillProjection
import com.courses.internshipapp.modules.students.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.UUID

interface InternshipRepository: JpaRepository<InternshipEntity, UUID> {
    fun findByCreatedBy(student: StudentEntity): List<InternshipEntity>

    @Query("""
    SELECT 
        i.internshipId as internshipId,
        i.title as title,
        i.description as description,
        i.status as status,
        i.company.name as company_name,
        i.company.sector as company_sector,
        i.company.size as company_size,
        i.company.website as company_website,
        i.company.address as company_address,
        i.company.city as company_city,
        i.company.postalCode as company_postalCode,
        s.name as skill_name
    FROM InternshipEntity i
    LEFT JOIN i.skills s
    WHERE i.createdBy.userId = :studentId
""")
    fun findAllCardByStudentId(studentId: UUID): List<InternshipCardWithSkillProjection>
    fun findByStatusAndStartDateLessThanEqual(
        status: InternshipStatus,
        date: LocalDate
    ): List<InternshipEntity>

    fun findByStatusAndEndDateBefore(
        status: InternshipStatus,
        date: LocalDate
    ): List<InternshipEntity>
}
