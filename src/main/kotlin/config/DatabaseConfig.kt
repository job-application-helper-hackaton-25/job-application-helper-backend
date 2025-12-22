package config

import mapping.OffersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils

object DatabaseConfig {

    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/jobdb",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "postgres"
        )

        transaction {
            SchemaUtils.create(OffersTable)
        }
    }
}
