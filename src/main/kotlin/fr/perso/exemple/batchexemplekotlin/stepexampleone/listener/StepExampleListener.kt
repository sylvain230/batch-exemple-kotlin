package fr.perso.exemple.batchexemplekotlin.stepexampleone.listener

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.stereotype.Component

@Component
class StepExampleListener: StepExecutionListener  {

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        return super.afterStep(stepExecution)
    }

    override fun beforeStep(stepExecution: StepExecution) {
        super.beforeStep(stepExecution)
    }
}