package com.courses.internshipapp.modules.internship.schedulers

import com.courses.internshipapp.modules.internship.InternshipRepository
import com.courses.internshipapp.modules.internship.InternshipStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class InternshipStatusScheduler(
    private val internshipRepository: InternshipRepository
) {
    @Scheduled(cron = "0 1 0 * * *")
    fun startInternship() {
        val today = LocalDate.now();
        val internships = internshipRepository
            .findByStatusAndStartDateLessThanEqual(InternshipStatus.SUBMITTED, today);

        internships.forEach {
            it.status = InternshipStatus.IN_PROGRESS;
        }

        internshipRepository.saveAll(internships);
    }

    @Scheduled(cron = "0 2 0 * * *")
    fun completeInternships() {
        val today = LocalDate.now()
        val internships = internshipRepository
            .findByStatusAndStartDateLessThanEqual(InternshipStatus.IN_PROGRESS, today)

        internships.forEach {
            it.status = InternshipStatus.COMPLETED
        }

        internshipRepository.saveAll(internships)
    }

    @Scheduled(cron = "0 3 0 * * *") // tous les jours à 00:03, mais tu peux changer
    fun archiveOldInternships() {
        val twoYearsAgo = LocalDate.now().minusYears(2)
        val oldCompletedInternships = internshipRepository
            .findByStatusAndEndDateBefore(InternshipStatus.COMPLETED, twoYearsAgo)

        oldCompletedInternships.forEach {
            it.status = InternshipStatus.ARCHIVED
        }

        internshipRepository.saveAll(oldCompletedInternships)
    }
}