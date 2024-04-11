package fr.perso.exemple.batchexemplekotlin.stepexampleone.reader

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import mu.KotlinLogging
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemStreamException
import org.springframework.batch.item.ItemStreamReader
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.BiFunction

private val log = KotlinLogging.logger { }

@Component
class CompositeCursorItemReader<T>(
    var mapJdbcCursorItemReader: SortedMap<PersoBatchJdbcCursorItemReader<*>, BiFunction<*, *, ContractInvoice>>
): ItemStreamReader<ContractInvoice> {

    override fun read(): ContractInvoice? {
        var customObjectDto: ContractInvoice? = null
        for (cursorItemReader in mapJdbcCursorItemReader.keys) { // On boucle sur le PersoBatchJdbcCursorItemReader
            log.info { "reading with reader ${cursorItemReader.ordre}" }
            val objectToRead = cursorItemReader.read() // Lecture du reader
            if (objectToRead != null) {
                // On récupère le mapper associé et on map.
                val mapper = mapJdbcCursorItemReader[cursorItemReader]
                if (mapper != null) {
                    customObjectDto = apply(objectToRead, customObjectDto, mapper as BiFunction<Any, ContractInvoice?, ContractInvoice>)
                }
            }
        }

        if (customObjectDto != null) {
            log.info { "object read : $customObjectDto" }
            return customObjectDto
        }

        return null

    }

    @Throws(ItemStreamException::class)
    override fun open(executionContext: ExecutionContext) {
        for (cursorItemReader in mapJdbcCursorItemReader.keys) {
            cursorItemReader.open(executionContext)
        }
    }

    @Throws(ItemStreamException::class)
    override fun update(executionContext: ExecutionContext) {
        for (cursorItemReader in mapJdbcCursorItemReader.keys) {
            cursorItemReader.update(executionContext)
        }
    }

    @Throws(ItemStreamException::class)
    override fun close() {
        for (cursorItemReader in mapJdbcCursorItemReader.keys) {
            cursorItemReader.close()
        }
    }
}

private fun Any.apply(objectToRead: Any, customObjectDto: ContractInvoice?, mapper: BiFunction<Any, ContractInvoice?, ContractInvoice>): ContractInvoice {
    return mapper.apply(objectToRead, customObjectDto)
}
