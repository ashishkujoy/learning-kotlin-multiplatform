package org.learning.adaptor


import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.learning.adaptor.repository.ShoppingListRepository
import org.learning.domain.ShoppingListItem
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

class Server {
    companion object {
        private val shoppingListRepository = ShoppingListRepository()

        @JvmStatic
        fun main(args: Array<String>) {
            embeddedServer(Netty, 9090) {
                install(ContentNegotiation) {
                    json()
                }
                install(CallLogging) {
                    logger = LoggerFactory.getLogger(this::class.java)
                    level = Level.INFO
                }
                install(CORS) {
                    method(HttpMethod.Get)
                    method(HttpMethod.Post)
                    method(HttpMethod.Delete)
                    anyHost()
                }
                install(Compression) {
                    gzip()
                }
                routing {
                    get("/hello") {
                        call.respondText("Hello, API!")
                    }
                    route(ShoppingListItem.path) {
                        get {
                            call.respond(shoppingListRepository.getShoppingList())
                        }
                        post {
                            val newItem = call.receive<ShoppingListItem>()
                            shoppingListRepository.add(newItem)
                            call.respond(HttpStatusCode.Accepted)
                        }
                        delete("/{id}") {
                            val itemId = call.parameters["id"]?.toIntOrNull()
                            if (itemId == null) {
                                call.respond(
                                    HttpStatusCode.BadRequest,
                                    "requite a valid item id"
                                )
                            } else {
                                shoppingListRepository.removeItemById(itemId)
                                call.respond(
                                    HttpStatusCode.Accepted
                                )
                            }
                        }
                    }
                }
            }.start(wait = true)
        }
    }
}
