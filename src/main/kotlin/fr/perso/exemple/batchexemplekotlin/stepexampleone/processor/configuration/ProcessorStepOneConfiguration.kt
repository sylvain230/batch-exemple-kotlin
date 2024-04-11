package fr.perso.exemple.batchexemplekotlin.stepexampleone.processor.configuration

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import fr.perso.exemple.batchexemplekotlin.stepexampleone.processor.validator.ValidatorContractInvoice
import org.springframework.batch.item.validator.ValidatingItemProcessor
import org.springframework.batch.item.validator.Validator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProcessorStepOneConfiguration {

    @Bean
    fun validateProcessor(
        validatorContractInvoice: ValidatorContractInvoice
    ) : ValidatingItemProcessor<ContractInvoice> {

        val validatingItemProcessor = ValidatingItemProcessor<ContractInvoice>()
        validatingItemProcessor.setValidator(validatorContractInvoice)

        return validatingItemProcessor
    }
}