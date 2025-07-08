package com.courses.internshipapp

import com.courses.internshipapp.modules.skills.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class SkillServiceTest {

    @Mock
    lateinit var skillRepository: SkillRepository

    @InjectMocks
    lateinit var skillService: SkillService

    private val sampleId = UUID.randomUUID()
    private val sampleEntity = SkillEntity(sampleId, "Kotlin")
    private val sampleRequest = SkillRequest(name = "Kotlin")
    private val sampleResponse = SkillResponse(id = sampleId, name = "Kotlin")

    @BeforeEach
    fun setup() {
        reset(skillRepository)
    }

    @Test
    fun `getAll should return all skills`() {
        `when`(skillRepository.findAll()).thenReturn(listOf(sampleEntity))

        val result = skillService.getAll()

        assertEquals(1, result.size)
        assertEquals("Kotlin", result[0].name)
        verify(skillRepository).findAll()
    }

    @Test
    fun `getById should return skill when found`() {
        `when`(skillRepository.findById(sampleId)).thenReturn(Optional.of(sampleEntity))

        val result = skillService.getById(sampleId)

        assertEquals("Kotlin", result.name)
        verify(skillRepository).findById(sampleId)
    }

    @Test
    fun `getById should throw when skill not found`() {
        `when`(skillRepository.findById(sampleId)).thenReturn(Optional.empty())

        val exception = assertThrows(RuntimeException::class.java) {
            skillService.getById(sampleId)
        }

        assertEquals("Skill not found", exception.message)
        verify(skillRepository).findById(sampleId)
    }

    @Test
    fun `create should save and return skill`() {
        `when`(skillRepository.save(any())).thenReturn(sampleEntity)

        val result = skillService.create(sampleRequest)

        assertEquals("Kotlin", result.name)
        verify(skillRepository).save(any())
    }

    @Test
    fun `createMany should save list of skills`() {
        val entities = listOf(sampleEntity)
        val requests = listOf(sampleRequest)

        `when`(skillRepository.saveAll(anyList())).thenReturn(entities)

        val result = skillService.createMany(requests)

        assertEquals(1, result.size)
        assertEquals("Kotlin", result[0].name)
        verify(skillRepository).saveAll(anyList())
    }
}
