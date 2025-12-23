import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Offer
import service.OfferService

fun Route.offerRoutes() {
    val repository = OfferService()

    route("/offers") {
        get {
            call.respond(repository.getAllOffers())
        }

        get("/statuses") {
            call.respond(model.OfferStatus.entries.map { 
                mapOf(
                    "value" to it.name,
                    "label" to it.name.replace("_", " ").lowercase().replaceFirstChar { char -> char.uppercase() }
                )
            })
        }

        post {
            val offer = call.receive<Offer>()
            val id = repository.insert(offer)
            call.respond(HttpStatusCode.Created, mapOf("id" to id))
        }

        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            
            val offer = call.receive<Offer>()
            val updated = repository.update(id, offer)
            if (updated != null) {
                call.respond(HttpStatusCode.OK, updated)
            } else {
                call.respond(HttpStatusCode.NotFound, "Offer not found")
            }
        }

        patch("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@patch call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            
            val updates = call.receive<Map<String, String>>()
            val updated = repository.partialUpdate(id, updates)
            if (updated != null) {
                call.respond(HttpStatusCode.OK, updated)
            } else {
                call.respond(HttpStatusCode.NotFound, "Offer not found")
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
