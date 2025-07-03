package com.courses.internshipapp.core.services

import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.context.Context
import org.xhtmlrenderer.pdf.ITextRenderer

@Service
class PdfService(
    private val templateEngine: SpringTemplateEngine
) {

    fun generateStaticPdf(templateName: String, response: HttpServletResponse) {
        try {
            val context = Context()

            val htmlContent = templateEngine.process(templateName, context)

            val renderer = ITextRenderer()
            renderer.setDocumentFromString(htmlContent)
            renderer.layout()

            response.contentType = "application/pdf"
            response.setHeader("Content-Disposition", "attachment; filename=convention_stage.pdf")
            val outputStream = response.outputStream
            renderer.createPDF(outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
