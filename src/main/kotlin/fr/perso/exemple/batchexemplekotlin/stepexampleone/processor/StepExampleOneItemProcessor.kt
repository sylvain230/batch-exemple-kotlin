package fr.perso.exemple.batchexemplekotlin.stepexampleone.processor

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class StepExampleOneItemProcessor : ItemProcessor<ContractInvoice, ContractInvoice> {
    override fun process(item: ContractInvoice): ContractInvoice {
        return item
    }
}