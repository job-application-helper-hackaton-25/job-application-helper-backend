package mapping

import org.jetbrains.exposed.dao.id.IntIdTable

object NotesTable : IntIdTable("notes") {
    val userId = varchar("user_id", 36)
    val offerId = varchar("offer_id", 36)
    val stage = varchar("stage", 100)
    val content = text("content")
    val date = varchar("date", 50)
}
