package fr.perso.exemple.batchexemplekotlin.stepexampletwo.writer

import fr.perso.exemple.batchexemplekotlin.model.ContractInvoice
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.xml.StaxEventItemWriter
import org.springframework.core.io.WritableResource
import org.springframework.oxm.Marshaller
import org.springframework.oxm.xstream.XStreamMarshaller
import org.springframework.stereotype.Component

@Component
class StepExampleTwoItemWriter(
    val outputResourceStepTwo: WritableResource
): StaxEventItemWriter<ContractInvoice>() {

    override fun afterPropertiesSet() {
        name = "WriterStepTwo"
        rootTagName = "contractInvoice"
        setResource(outputResourceStepTwo)
        setMarshaller(createContractInvoiceMarshaller())
        setOverwriteOutput(true)
    }

    fun createContractInvoiceMarshaller(): XStreamMarshaller {
        val marshaller = XStreamMarshaller()
        val aliases = mutableMapOf(
            Pair("contractInvoice", ContractInvoice::class.java),
            Pair("numInvoice", String::class.java),
            Pair("numContract", String::class.java),
        )
        marshaller.setAliases(aliases)

        return marshaller
    }

    override fun write(items: Chunk<out ContractInvoice>) {
        super.write(items)
    }
}