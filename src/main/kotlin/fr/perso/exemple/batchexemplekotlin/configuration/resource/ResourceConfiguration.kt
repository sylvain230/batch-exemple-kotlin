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

    @Bean
    fun outputResourceStepOne(
        @Value("\${output.step-one.dir}") outputDir: String,
        @Value("\${output.step-one.name}") name: String
    ): WritableResource {
        return getResource(outputDir, name, "Output stepOne Files")
    }

    @Bean
    fun inputResourceStepTwo(
        @Value("\${input.step-two.dir}") outputDir: String,
        @Value("\${input.step-two.name}") name: String
    ): WritableResource {
        return getResource(outputDir, name, "Input stepTwo Files")
    }

    @Bean
    fun outputResourceStepTwo(
        @Value("\${output.step-two.dir}") outputDir: String,
        @Value("\${output.step-two.name}") name: String
    ): WritableResource {
        return getResource(outputDir, name, "Output stepTwo Files")
    }

    private fun getResource(outputDir: String, name: String, infos: String): WritableResource {
        val outputFile = File(
            outputDir, String.format(name)
        )
        log.info { "$name of $infos will be written to ${outputFile.absolutePath}" }
        return FileSystemResource(outputFile)
    }
}