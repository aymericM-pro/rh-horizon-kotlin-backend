import com.courses.internshipapp.modules.defenses.DefenseMapper
import com.courses.internshipapp.modules.internship.InternshipEntity
import com.courses.internshipapp.modules.skills.SkillEntity
import com.courses.internshipapp.modules.internship.dtos.*
import com.courses.internshipapp.modules.internship.entities.*
import com.courses.internshipapp.modules.students.StudentEntity
import java.time.LocalDate

object InternshipMapper {

    fun toEntity(
        request: InternshipCreate,
        student: StudentEntity,
        skills: Set<SkillEntity>
    ): InternshipEntity {
        val internship = InternshipEntity(
            title = request.title,
            description = request.description,
            duration = request.duration,
            startDate = LocalDate.parse(request.startDate),
            endDate = LocalDate.parse(request.endDate),
            year = request.year,
            type = request.type,
            major = request.major,
            createdBy = student,
            company = CompanyDetails(
                name = request.company.name,
                sector = request.company.sector,
                size = request.company.size,
                website = request.company.website,
                address = request.company.address,
                city = request.company.city,
                postalCode = request.company.postalCode
            ),
            supervisor = SupervisorDetails(
                firstName = request.supervisor.firstName,
                lastName = request.supervisor.lastName,
                position = request.supervisor.position,
                department = request.supervisor.department,
                email = request.supervisor.email,
                phone = request.supervisor.phone
            ),
            documents = DocumentLinks(
                cvUrl = request.documents.cvUrl,
                coverLetterUrl = request.documents.coverLetterUrl,
                transcriptUrl = request.documents.transcriptUrl,
                agreementUrl = request.documents.agreementUrl
            ),
            agreements = AgreementStatus(
                dataProcessing = request.agreements.dataProcessing,
                terms = request.agreements.terms,
                truthfulness = request.agreements.truthfulness
            ),
            skills = skills.toMutableSet(),
            missions = mutableListOf()
        )

        internship.missions = request.missions.map {
            InternshipMissionEntity(description = it, internship = internship)
        }.toMutableList()

        return internship
    }

    fun toResponse(entity: InternshipEntity): InternshipResponse =
        InternshipResponse(
            internshipId = entity.internshipId,
            title = entity.title,
            description = entity.description,
            duration = entity.duration,
            startDate = entity.startDate.toString(),
            endDate = entity.endDate.toString(),
            year = entity.year,
            type = entity.type,
            major = entity.major,
            validated = entity.validated,
            status = entity.status,
            studentId = entity.createdBy.userId,
            validatedBy = entity.validatedBy?.userId,
            company = CompanyDto(
                name = entity.company.name,
                sector = entity.company.sector,
                size = entity.company.size,
                website = entity.company.website,
                address = entity.company.address,
                city = entity.company.city,
                postalCode = entity.company.postalCode
            ),
            supervisor = SupervisorDto(
                firstName = entity.supervisor.firstName,
                lastName = entity.supervisor.lastName,
                position = entity.supervisor.position,
                department = entity.supervisor.department,
                email = entity.supervisor.email,
                phone = entity.supervisor.phone
            ),
            documents = DocumentDto(
                cvUrl = entity.documents.cvUrl,
                coverLetterUrl = entity.documents.coverLetterUrl,
                transcriptUrl = entity.documents.transcriptUrl,
                agreementUrl = entity.documents.agreementUrl
            ),
            agreements = AgreementDto(
                dataProcessing = entity.agreements.dataProcessing,
                terms = entity.agreements.terms,
                truthfulness = entity.agreements.truthfulness
            ),
            skills = entity.skills.map { it.name },
            missions = entity.missions.map { it.description },
            defenses = entity.defense?.let { DefenseMapper.toResponse(it) }
        )
}
