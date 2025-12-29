package mapping

import org.jetbrains.exposed.dao.id.IntIdTable

object TodosTable : IntIdTable("todos") {
    val userId = varchar("user_id", 36)
    val offerId = varchar("offer_id", 36)
    val content = text("content")
    val deadline = varchar("deadline", 50)
    val priority = varchar("priority", 50)
    val completed = bool("completed")
}