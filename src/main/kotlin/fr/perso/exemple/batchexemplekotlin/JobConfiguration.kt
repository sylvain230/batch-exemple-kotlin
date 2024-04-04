package fr.perso.exemple.batchexemplekotlin

import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


private val log = KotlinLogging.logger {  }

@EnableBatchProcessing
@Configuration
class JobConfiguration  (
    private val jobRepository: JobRepository
) {

    @Bean
    fun job(
        @Qualifier("stepExampleOne") stepExampleUn: Step,
        @Qualifier("stepExampleTwo") stepExampleDeux: Step): Job {
        return JobBuilder("myJob", jobRepository)
            .start(stepExampleUn)
            .next(stepExampleDeux)
            .build()
    }
}
