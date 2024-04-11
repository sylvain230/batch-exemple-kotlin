package fr.perso.exemple.batchexemplekotlin.stepexampleone.listener

import mu.KotlinLogging
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

private val log = KotlinLogging.logger { }

@Component
class StepExampleListener: StepExecutionListener  {

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        log.info("STATUS STEP :: ${stepExecution.status}")
        return super.afterStep(stepExecution)
    }

    override fun beforeStep(stepExecution: StepExecution) {
        log.info("Running step ${stepExecution.stepName} (v{${stepExecution.version}})")
        super.beforeStep(stepExecution)
    }
}