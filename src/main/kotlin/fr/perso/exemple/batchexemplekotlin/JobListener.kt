package fr.perso.exemple.batchexemplekotlin

import mu.KotlinLogging
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger { }

@Component
class JobListener: JobExecutionListener {

    override fun beforeJob(jobExecution: JobExecution) {
        log.info("Running job ${jobExecution.jobId} (v{${jobExecution.version}})")
    }

    override fun afterJob(jobExecution: JobExecution) {
        log.info("job status : ${jobExecution.status}")
    }
}