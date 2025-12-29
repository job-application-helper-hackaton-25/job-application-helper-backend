import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Note
import service.NoteService

fun Route.noteRoutes() {
    val repository = NoteService()

    route("offers/{offerId}/notes") {

        get {
            val offerId = call.parameters["offerId"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val notes = repository.findByOfferId(offerId)
            call.respond(HttpStatusCode.OK, notes)
        }

        post {
            val offerId = call.parameters["offerId"]
                ?: return@post call.respond(HttpStatusCode.BadRequest)

            var note = call.receive<Note>()
            val created = repository.insert(note, offerId)

            call.respond(HttpStatusCode.Created, created)
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = repository.deleteById(id)
            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "Offer not found")
            }
        }
    }
}