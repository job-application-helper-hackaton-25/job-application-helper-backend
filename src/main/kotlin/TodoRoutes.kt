import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Todo
import service.TodoService

fun Route.todoRoutes() {
    val repository = TodoService()

    route("/offers/{offerId}/todos") {

        get {
            val offerId = call.parameters["offerId"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val todos = repository.findByOfferId(offerId)
            call.respond(HttpStatusCode.OK, todos)
        }

        post {
            val offerId = call.parameters["offerId"]
                ?: return@post call.respond(HttpStatusCode.BadRequest)

            val todo = call.receive<Todo>()
            val created = repository.insert(todo, offerId)

            call.respond(HttpStatusCode.Created, created)
        }

        patch("{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@patch call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val completed = call.receive<Boolean>()
            val statusChange = repository.updateStatus(id, completed)
            if (statusChange != null) {
                call.respond(HttpStatusCode.OK, statusChange)
            } else {
                call.respond(HttpStatusCode.NotAcceptable, "Status change incorrect")
            }
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