package config

import mapping.NotesTable
import mapping.OffersTable
import mapping.TodosTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {

    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/jobdb",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "postgres"
        )

        transaction {
            SchemaUtils.createMissingTablesAndColumns(OffersTable)
            SchemaUtils.createMissingTablesAndColumns(NotesTable)
            SchemaUtils.createMissingTablesAndColumns(TodosTable)
        }
    }
}
