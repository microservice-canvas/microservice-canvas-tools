package io.microservices.canvas.asciidoc

import io.microservices.canvas.model.Service


class Canvas(service : Service) {

    val name = service.name
    val description = service.description
    val capabilities = service.capabilities
    val apiCommands = service.api.operations.commands
    val apiQueries = service.api.operations.queries
    val apiEvents = service.api.events.publishedEvents

    val dependenciesAsynchronousOperations = service.dependencies.asynchronousOperations
    val dependenciesEvents = service.dependencies.events
}