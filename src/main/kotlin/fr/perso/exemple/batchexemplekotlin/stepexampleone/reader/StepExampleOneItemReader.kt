package fr.perso.exemple.batchexemplekotlin.stepexampleone.reader

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.ResultSet
import javax.sql.DataSource

private const val SQL_SELECT = """
    SELECT num_contract, label FROM contract
"""

@Configuration
class StepExampleOneItemReader {

    @Bean
    fun readerExampleOne(dataSource: DataSource) : ItemReader<ContractInvoice> {
        val reader = JdbcCursorItemReader<ContractInvoice>()
        reader.sql = SQL_SELECT
        reader.name = "readerStepExampleOne"
        reader.setRowMapper { rs: ResultSet, _ ->
            ContractInvoice(
                rs.getString("num_contract"),
                rs.getString("label")
            )
        }
        reader.dataSource = dataSource
        return reader
    }

}