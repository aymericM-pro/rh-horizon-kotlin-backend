package com.courses.internshipapp.core.services

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pdf")
class PdfController(
    private val pdfService: PdfService
) {

    @GetMapping("/static-convention")
    fun downloadStaticConvention(response: HttpServletResponse) {
        pdfService.generateStaticPdf("convention", response)
    }
}
