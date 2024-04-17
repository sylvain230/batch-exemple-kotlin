package fr.perso.exemple.batchexemplekotlin.stepexampletwo.processor

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class StepExampleTwoItemProcessor: ItemProcessor<ContractInvoice, ContractInvoice> {
    override fun process(item: ContractInvoice): ContractInvoice? {
        return item
    }
}