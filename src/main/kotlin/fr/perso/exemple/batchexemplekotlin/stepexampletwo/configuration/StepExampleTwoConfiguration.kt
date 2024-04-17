package fr.perso.exemple.batchexemplekotlin.stepexampletwo.configuration

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.xml.StaxEventItemWriter
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.WritableResource
import org.springframework.oxm.xstream.XStreamMarshaller
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class StepExampleTwoConfiguration {

    @Bean
    fun stepExampleTwo(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        stepExampleTwoItemProcessor: ItemProcessor<ContractInvoice, ContractInvoice>,
        stepExampleTwoItemWriter: StaxEventItemWriter<ContractInvoice>,
        inputResourceStepTwo: WritableResource,
        outputResourceStepTwo: WritableResource) : Step {
        return StepBuilder("stepExampleTwo", jobRepository)
            .chunk<ContractInvoice,ContractInvoice>(5, transactionManager)
            .reader (
                FlatFileItemReaderBuilder<ContractInvoice>()
                    .name("ReaderStepTwo")
                    .resource(inputResourceStepTwo)
                    .lineMapper(createLineMapper())
                    .linesToSkip(1)
                    .build()
            ).processor(stepExampleTwoItemProcessor)
            .writer(stepExampleTwoItemWriter)
            .build()
    }

    fun createContractInvoiceMarshaller(): XStreamMarshaller {
        val marshaller = XStreamMarshaller()
        val aliases = mutableMapOf(
            Pair("contractInvoice", ContractInvoice::class.java),
            Pair("numContract", String::class.java),
            Pair("numInvoice", String::class.java)
        )
        marshaller.setAliases(aliases)

        return marshaller

    }

    fun createLineMapper(): DefaultLineMapper<ContractInvoice> {
        val mapper = DefaultLineMapper<ContractInvoice>()

        val lineTokenizer = DelimitedLineTokenizer()
        lineTokenizer.setDelimiter(";")
        lineTokenizer.setNames(*arrayOf("numContract", "numInvoice"))
        lineTokenizer.setStrict(false)

        val contractInvoiceMapper = BeanWrapperFieldSetMapper<ContractInvoice>()
        contractInvoiceMapper.setTargetType(ContractInvoice::class.java)
        mapper.setFieldSetMapper(contractInvoiceMapper)
        mapper.setLineTokenizer(lineTokenizer)

        return mapper
    }
}
