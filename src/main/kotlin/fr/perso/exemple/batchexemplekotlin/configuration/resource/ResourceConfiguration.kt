package fr.perso.exemple.batchexemplekotlin.configuration.resource

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.WritableResource
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

private val log = KotlinLogging.logger {}

private val FORMATTER = DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .append(DateTimeFormatter.ISO_LOCAL_DATE)
    .appendLiteral('T')
    .appendPattern("HH-mm-ss-SSS")
    .toFormatter()



@Configuration
class ResourceConfiguration {

    private fun getResource(outputDir: String, successFilePattern: String, s: String): WritableResource {
        val outputFile = File(
            outputDir, String.format(
                successFilePattern,
                LocalDateTime.now().format(FORMATTER)
            )
        )
        log.info { "$s will be written to ${outputFile.absolutePath}" }
        return FileSystemResource(outputFile)
    }

    @Bean
    fun outputResourceStepOne(
        @Value("\${output.step-one.generate.dir}") outputDir: String,
        @Value("\${output.step-one.generate.success.file-pattern}") successFilePattern: String
    ): WritableResource {
        return getResource(outputDir, successFilePattern, "Output stepOne Files")
    }
}