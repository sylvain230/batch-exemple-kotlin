package fr.perso.exemple.batchexemplekotlin.stepexampleone.processor.validator

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import mu.KotlinLogging
import org.springframework.batch.item.validator.ValidationException
import org.springframework.batch.item.validator.Validator
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger { }

@Component
class ValidatorContractInvoice: Validator<ContractInvoice> {
    override fun validate(contractInvoice: ContractInvoice) {
        if(contractInvoice.numInvoice?.contains("20") == true) {
            log.warn { "Object $contractInvoice skipped" }
            throw ValidationException("Object $contractInvoice skipped")
        }
    }
}