package fr.perso.exemple.batchexemplekotlin.stepexampleone.configuration

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import fr.perso.exemple.batchexemplekotlin.stepexampleone.listener.StepExampleListener
import fr.perso.exemple.batchexemplekotlin.stepexampleone.processor.StepExampleOneItemProcessor
import fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.CompositeCursorItemReader
import fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.populate.PopulateContractInvoiceWithContractDto
import fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.populate.PopulateContractInvoiceWithInvoiceDto
import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.DelimitedLineAggregator
import org.springframework.batch.item.support.CompositeItemProcessor
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder
import org.springframework.batch.item.validator.ValidatingItemProcessor
import org.springframework.batch.item.validator.ValidationException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.WritableResource
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@Configuration
class StepExampleOneConfiguration {

    @Bean
    fun stepExampleOne(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        @Qualifier("itemReaderCustom") itemReaderCustom: CompositeCursorItemReader<ContractInvoice>,
        compositeItemProcessor: CompositeItemProcessor<ContractInvoice, ContractInvoice>,
        @Qualifier("itemWriterStepOne") itemWriterStepOne: FlatFileItemWriter<ContractInvoice>,
        stepListener: StepExampleListener,
    ) : Step {
        return StepBuilder("stepExampleOne", jobRepository)
            .chunk<ContractInvoice, ContractInvoice> (10, transactionManager)
            .listener(stepListener)
            .reader(itemReaderCustom)
            .processor(compositeItemProcessor)
            .writer(itemWriterStepOne)
            .faultTolerant()
            .skipLimit(5)
            .skip(ValidationException::class.java)
            .build()
    }

    @Bean
    fun compositeItemProcessor(
        itemProcessor: StepExampleOneItemProcessor,
        validateProcessor: ValidatingItemProcessor<ContractInvoice>
    ) : CompositeItemProcessor<ContractInvoice, ContractInvoice> {
        return CompositeItemProcessorBuilder<ContractInvoice, ContractInvoice>()
                .delegates(mutableListOf(itemProcessor, validateProcessor))
                .build()


    }

    @Bean
    fun itemWriterStepOne(
        outputResourceStepOne: WritableResource,
        @Value("\${input.file.row.separator:;}") csvSeparator: String,
    ): FlatFileItemWriter<ContractInvoice> {
        val fieldExtractor: BeanWrapperFieldExtractor<ContractInvoice> = BeanWrapperFieldExtractor()
        fieldExtractor.setNames(arrayOf("numContract", "numInvoice"))

        val delimitedLineAggregator: DelimitedLineAggregator<ContractInvoice> = DelimitedLineAggregator()
        delimitedLineAggregator.setDelimiter(csvSeparator)
        delimitedLineAggregator.setFieldExtractor(fieldExtractor)

        return FlatFileItemWriterBuilder<ContractInvoice>()
            .name("itemWriterStepOne")
            .resource(outputResourceStepOne)
            .lineAggregator(delimitedLineAggregator)
            .build()
    }
}