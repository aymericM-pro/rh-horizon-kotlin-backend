package com.courses.internshipapp.modules.defenses

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DefenseStatusScheduler(
    private val defenseRepository: DefenseRepository
) {

    @Scheduled(cron = "0 5 0 * * *")
    fun markDefenseAsPassed() {
        val today = LocalDate.now()

        val defensesToMarkAsPassed = defenseRepository.findAll()
            .filter { it.status == DefenseStatus.SCHEDULED && it.preferredDate.isBefore(today) }

        defensesToMarkAsPassed.forEach {
            it.status = DefenseStatus.PASSED
        }

        defenseRepository.saveAll(defensesToMarkAsPassed)
    }
}
