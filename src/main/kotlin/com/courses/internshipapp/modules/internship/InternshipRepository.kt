package com.courses.internshipapp.modules.internship

import com.courses.internshipapp.modules.students.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface InternshipRepository: JpaRepository<InternshipEntity, UUID> {
    fun findByStudent(student: StudentEntity): List<InternshipEntity>
}