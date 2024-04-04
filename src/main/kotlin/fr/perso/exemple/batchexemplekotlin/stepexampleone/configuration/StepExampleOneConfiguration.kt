package fr.perso.exemple.batchexemplekotlin.stepexampleone.configuration

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import fr.perso.exemple.batchexemplekotlin.stepexampleone.listener.StepExampleListener
import fr.perso.exemple.batchexemplekotlin.stepexampleone.processor.StepExampleOneItemProcessor
import fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.StepExampleOneItemReader
import fr.perso.exemple.batchexemplekotlin.stepexampleone.writer.StepExampleOneItemWriter
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepListener
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.LineAggregator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class StepExampleOneConfiguration {

    @Bean
    fun stepExampleOne(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        @Qualifier("readerExampleOne")
        itemReader: ItemReader<ContractInvoice>,
        itemProcessor: StepExampleOneItemProcessor,
        @Qualifier("itemWriterStepOne")
        itemWriter: FlatFileItemWriter<String>,
        stepListener: StepExampleListener,
    ) : Step {
        return StepBuilder("stepExampleOne", jobRepository)
            .chunk<ContractInvoice, String> (10, transactionManager)
            .listener(stepListener)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build()
    }
}