package fr.perso.exemple.batchexemplekotlin

import mu.KotlinLogging
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

private val log = KotlinLogging.logger { }

@SpringBootApplication
class BatchExempleKotlinApplication(
	val jobLauncher: JobLauncher,
	val job: Job
): CommandLineRunner {
	override fun run(vararg args: String?) {
		val execution: JobExecution = jobLauncher.run(job, JobParameters())
		log.info { "STATUS :: ${execution.status}" }

		val exitCode  = when (execution.status) {
			BatchStatus.COMPLETED -> 0
			BatchStatus.ABANDONED -> 4
			BatchStatus.FAILED -> 5
			else -> 1
		}
		exitProcess(exitCode)
	}

}

fun main(args: Array<String>) {
	runApplication<BatchExempleKotlinApplication>(*args)
}
