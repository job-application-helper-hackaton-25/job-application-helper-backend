package service

import mapping.NotesTable
import model.Note
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class NoteService {

    fun findByOfferId(offerId: String): List<Note> =
        transaction {
            NotesTable
                .select {
                    (NotesTable.offerId eq offerId)
                }
                .map {
                    Note(
                        id = it[NotesTable.id].value,
                        userId = it[NotesTable.userId],
                        offerId = it[NotesTable.offerId],
                        stage = it[NotesTable.stage],
                        content = it[NotesTable.content],
                        date = it[NotesTable.date]
                    )
                }
        }

    fun insert(note: Note, offerId: String): Int =
        transaction {
            NotesTable.insertAndGetId {
                it[NotesTable.userId] = note.userId
                it[NotesTable.offerId] = offerId
                it[NotesTable.stage] = note.stage
                it[NotesTable.content] = note.content
                it[NotesTable.date] = note.date
            }.value
        }

    fun deleteById(id: Int): Boolean =
        transaction {
            NotesTable.deleteWhere { NotesTable.id eq id } > 0
        }
}