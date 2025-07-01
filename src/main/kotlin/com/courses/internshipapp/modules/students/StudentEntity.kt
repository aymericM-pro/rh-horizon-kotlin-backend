package com.courses.internshipapp.modules.students

import com.courses.internshipapp.modules.internship.InternshipEntity
import com.courses.internshipapp.modules.roles.Role
import com.courses.internshipapp.modules.users.UserEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@DiscriminatorValue("STUDENT")
class StudentEntity(
    name: String,
    email: String,
    password: String
) : UserEntity(name, email, password, Role.STUDENT) {

    @OneToMany(mappedBy = "createdBy", cascade = [CascadeType.ALL], orphanRemoval = true)
    var internshipsCreated: MutableSet<InternshipEntity> = mutableSetOf()
}
