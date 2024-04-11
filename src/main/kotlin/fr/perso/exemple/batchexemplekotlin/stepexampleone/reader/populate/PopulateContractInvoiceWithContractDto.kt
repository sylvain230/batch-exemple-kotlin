package fr.perso.exemple.batchexemplekotlin.stepexampleone.reader.populate

import fr.perso.exemple.batchexemplekotlin.model.ContractDto
import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import org.springframework.stereotype.Service
import java.util.function.BiFunction

@Service
class PopulateContractInvoiceWithContractDto: BiFunction<ContractDto, ContractInvoice?, ContractInvoice> {

    override fun apply(contractDto: ContractDto, contractInvoice: ContractInvoice?): ContractInvoice {
        val currentContractInvoice = contractInvoice ?: ContractInvoice()
        currentContractInvoice.numContract = contractDto.numContract
        return currentContractInvoice
    }
}