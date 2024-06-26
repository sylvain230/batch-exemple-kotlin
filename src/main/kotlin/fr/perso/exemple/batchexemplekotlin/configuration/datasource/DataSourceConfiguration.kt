package fr.perso.exemple.batchexemplekotlin.configuration.datasource

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "batch.datasource.example")
    fun exampleDatasource(): DataSource {
        return DataSourceBuilder.create().build()
    }
}