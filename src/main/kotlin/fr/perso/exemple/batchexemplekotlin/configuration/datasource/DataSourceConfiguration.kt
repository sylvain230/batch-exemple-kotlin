package fr.perso.exemple.batchexemplekotlin.configuration.datasource

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration {

    @Bean
    fun batchDataSource(): DataSource? {
        return EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
            .addScript("/org/springframework/batch/core/schema-hsqldb.sql")
            .generateUniqueName(true).build()
    }
}