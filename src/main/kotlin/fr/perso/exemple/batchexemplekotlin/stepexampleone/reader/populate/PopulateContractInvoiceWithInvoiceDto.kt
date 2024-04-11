package fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.populate

import fr.perso.exemple.batchexemplekotlin.model.ContractDto
import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import fr.perso.exemple.batchexemplekotlin.model.InvoiceDto
import org.springframework.stereotype.Service
import java.util.function.BiFunction

@Service
class PopulateContractInvoiceWithInvoiceDto: BiFunction<InvoiceDto, ContractInvoice?, ContractInvoice> {

    override fun apply(invoiceDto: InvoiceDto, contractInvoice: ContractInvoice?): ContractInvoice {
        val currentContractInvoice = contractInvoice ?: ContractInvoice()
        currentContractInvoice.numInvoice = invoiceDto.numInvoice
        return currentContractInvoice
    }
}