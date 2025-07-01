package com.courses.internshipapp.modules.students

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StudentRepository : JpaRepository<StudentEntity, UUID>
