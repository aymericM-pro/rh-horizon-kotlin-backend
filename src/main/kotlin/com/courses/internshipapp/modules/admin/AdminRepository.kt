package com.courses.internshipapp.modules.admin

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AdminRepository : JpaRepository<AdminEntity, UUID>