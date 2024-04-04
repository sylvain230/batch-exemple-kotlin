package fr.perso.exemple.batchexemplekotlin.stepexampleone.writer

import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.stereotype.Component

@Component
class StepExampleOneItemWriter : FlatFileItemWriter<String>() {

    override fun afterPropertiesSet() {
        super.afterPropertiesSet()
    }
}