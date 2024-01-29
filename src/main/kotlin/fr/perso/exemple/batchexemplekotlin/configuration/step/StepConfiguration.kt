package fr.perso.exemple.batchexemplekotlin.configuration.step

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class StepConfiguration(
    @Value("\${input.file.row.separator}")
    private val separator: String
) {

    @Bean
    fun stepExemple(
        transactionManager: PlatformTransactionManager,
        jobRepository: JobRepository
    ): Step {
        return StepBuilder("monStep",jobRepository)
            .chunk<ContractInvoice, ContractInvoice>(10, transactionManager)
            .reader {

                val reader: FlatFileItemReader<ContractInvoice> = FlatFileItemReader<ContractInvoice>()
                reader.setLineMapper(DefaultLineMapper())
                reader.setLinesToSkip(1)

                val lineMapper: DefaultLineMapper<ContractInvoice> = DefaultLineMapper()
                val delimitedLineTokenizer = DelimitedLineTokenizer(separator)

                lineMapper.setLineTokenizer(delimitedLineTokenizer)
                lineMapper.setFieldSetMapper{
                    ContractInvoice(
                        it.readString(0),
                        it.readString(1)
                    )
                }

                reader.read()
            }
            .processor {
                it.invoiceId = "nouveau numéro ${it.invoiceId}"
                it
            }
            .writer {
                FlatFileItemWriterBuilder<ContractInvoice>().build()
            }
            .build()
    }
}