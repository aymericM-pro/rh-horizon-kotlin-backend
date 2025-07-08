package com.courses.internshipapp

import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertEquals
import org.mockito.Mockito.`when` as whenever

class MockitoSimpleTest {
    interface HelloService {
        fun greet(): String
    }

    private lateinit var helloService: HelloService

    @BeforeEach
    fun setUp() {
        helloService = mock(HelloService::class.java)
    }

    @Test
    fun `test greet`() {
        whenever(helloService.greet()).thenReturn("Hello Mockito")

        val result = helloService.greet()

        assertEquals("Hello Mockito", result)
        verify(helloService).greet()
    }
}
