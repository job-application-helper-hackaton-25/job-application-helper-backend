package com.hackaton

import config.DatabaseConfig
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.routing
import offerRoutes
import plugins.configureRouting

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {
    configureMonitoring()
    configureHTTP()
    configureSerialization()
    configureFrameworks()
    configureRouting()

    DatabaseConfig.init()

    routing {
        offerRoutes()
    }
}
