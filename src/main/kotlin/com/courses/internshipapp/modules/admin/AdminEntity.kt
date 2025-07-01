package com.courses.internshipapp.modules.admin

import com.courses.internshipapp.modules.internship.InternshipEntity
import com.courses.internshipapp.modules.roles.Role
import com.courses.internshipapp.modules.users.UserEntity
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
class AdminEntity(
    name: String,
    email: String,
    password: String
) : UserEntity(name, email, password, Role.ADMIN) {

    @OneToMany(mappedBy = "validatedBy")
    var internshipsValidated: MutableSet<InternshipEntity> = mutableSetOf()
}
