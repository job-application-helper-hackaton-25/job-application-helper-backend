package plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Offer
import service.OfferService

fun Application.configureRouting() {

    fun Route.offerRoutes() {
        val repository = OfferService()

        route("/offers") {
            get {
                call.respond(repository.getAllOffers())
            }

            post {
                val offer = call.receive<Offer>()
                val id = repository.insert(offer)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
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
}
