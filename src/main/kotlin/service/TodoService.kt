package service

import mapping.TodosTable
import model.Todo
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TodoService {

    fun findByOfferId(offerId: String): List<Todo> =
        transaction {
            TodosTable
                .select {
                    (TodosTable.offerId eq offerId)
                }
                .map {
                    Todo(
                        id = it[TodosTable.id].value,
                        userId = it[TodosTable.userId],
                        offerId = it[TodosTable.offerId],
                        content = it[TodosTable.content],
                        deadline = it[TodosTable.deadline],
                        priority = it[TodosTable.priority],
                        completed = it[TodosTable.completed]
                    )
                }
        }

    fun insert(todo: Todo, offerId: String): Int =
        transaction {
            TodosTable.insertAndGetId {
                it[TodosTable.userId] = todo.userId
                it[TodosTable.offerId] = offerId
                it[TodosTable.content] = todo.content

                todo.deadline?.let { deadline ->
                    it[TodosTable.deadline] = deadline
                }

                it[TodosTable.priority] = todo.priority
                it[TodosTable.completed] = todo.completed
            }.value
        }

    fun updateStatus(id: Int, completed: Boolean): Todo? =
        transaction {
            TodosTable.update({ TodosTable.id eq id }) { row ->
                row[TodosTable.completed] = completed
            }

            TodosTable.select { TodosTable.id eq id }.map {
                Todo(
                    id = it[TodosTable.id].value,
                    userId = it[TodosTable.userId],
                    offerId = it[TodosTable.offerId],
                    content = it[TodosTable.content],
                    deadline = it[TodosTable.deadline],
                    priority = it[TodosTable.priority],
                    completed = it[TodosTable.completed]
                )
            }.firstOrNull()
        }

    fun deleteById(id: Int): Boolean =
        transaction {
            TodosTable.deleteWhere { TodosTable.id eq id } > 0
        }
}