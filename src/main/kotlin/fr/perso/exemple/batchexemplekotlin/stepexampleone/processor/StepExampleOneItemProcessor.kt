package fr.perso.exemple.batchexemplekotlin.stepexampleone.processor

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import org.springframework.batch.item.ItemProcessor

class StepExampleOneItemProcessor : ItemProcessor<ContractInvoice, String> {
    override fun process(item: ContractInvoice): String {
        return item.contractId + item.invoiceId
    }
}