package io.microservices.canvas.model

import java.util.*

data class Dependencies(val asynchronousOperations: MutableList<InvokedChannelAndCommands>, val events: MutableList<SubscribedToAggregateAndEvents>)

data class InvokedChannelAndCommands(val channel : String, val operations : MutableSet<String>) {
    fun add(commandClass: String) {
        operations.add(commandClass)
    }

    constructor(commandChannel: String) : this(commandChannel, HashSet<String>())
}

data class SubscribedToAggregateAndEvents(val aggregateType : String, val eventTypes : MutableSet<String>) {
    fun add(eventType: String) {
        eventTypes.add(eventType)
    }

    constructor(aggregateType: String) : this(aggregateType, HashSet<String>())

}
