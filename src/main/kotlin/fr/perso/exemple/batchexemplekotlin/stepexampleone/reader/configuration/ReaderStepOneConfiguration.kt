package fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.configuration

import fr.perso.exemple.batchexemplekotlin.model.ContractDto
import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import fr.perso.exemple.batchexemplekotlin.model.InvoiceDto
import fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.CompositeCursorItemReader
import fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.PersoBatchJdbcCursorItemReader
import fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.populate.PopulateContractInvoiceWithContractDto
import fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.populate.PopulateContractInvoiceWithInvoiceDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.ResultSet
import java.util.*
import java.util.function.BiFunction
import javax.sql.DataSource


private const val SQL_SELECT_CONTRACT = """
    SELECT pk_contract_id, num_contract, label FROM schema_sdebras.contract
"""
private const val SQL_SELECT_INVOICE = """
    SELECT pk_invoice_id, num_invoice FROM schema_sdebras.invoice
"""

@Configuration
class ReaderStepOneConfiguration(
    val exampleDataSource: DataSource,
    val populateContractInvoiceWithContractDto: PopulateContractInvoiceWithContractDto,
    val populateContractInvoiceWithInvoiceDto: PopulateContractInvoiceWithInvoiceDto
) {

    @Bean
    fun itemReaderCustom(): CompositeCursorItemReader<ContractInvoice> {

        // Création d'une map pour pouvoir rajouter facilement des JdbcCursorItemReader.
        val map = TreeMap<PersoBatchJdbcCursorItemReader<*>, BiFunction<*, *, ContractInvoice>>()

        // On ajoute les readers dans la map.
        // Ils vont être lu par ordre alphabétique de nameReader.
        // Le nameReader est défini dans la méthode build() du PersoBatchJdbcCursorItemReaderBuilder.

        // On ajoute les readers dans la map.
        // Ils vont être lu par ordre alphabétique de nameReader.
        // Le nameReader est défini dans la méthode build() du PersoBatchJdbcCursorItemReaderBuilder.
        map[readerExampleOneContract()] = populateContractInvoiceWithContractDto
        map[readerExampleOneInvoice()] = populateContractInvoiceWithInvoiceDto

        return CompositeCursorItemReader(map)

    }

    @Bean
    fun readerExampleOneContract() : PersoBatchJdbcCursorItemReader<Any> {
        val reader = PersoBatchJdbcCursorItemReader<Any>()
        reader.sql = SQL_SELECT_CONTRACT
        reader.name = "reader for contracts"
        reader.ordre = "1"
        reader.setRowMapper { rs: ResultSet, _ ->
            ContractDto(
                id = rs.getInt("pk_contract_id"),
                numContract = rs.getString("num_contract"),
                label = rs.getString("label"),
            )
        }
        reader.dataSource = exampleDataSource
        return reader
    }

    @Bean
    fun readerExampleOneInvoice() : PersoBatchJdbcCursorItemReader<Any> {
        val reader = PersoBatchJdbcCursorItemReader<Any>()
        reader.sql = SQL_SELECT_INVOICE
        reader.name = "reader for invoices"
        reader.ordre = "2"
        reader.setRowMapper { rs: ResultSet, _ ->
            InvoiceDto(
                id = rs.getInt("pk_invoice_id"),
                numInvoice = rs.getString("num_invoice")
            )
        }
        reader.dataSource = exampleDataSource
        return reader
    }
}