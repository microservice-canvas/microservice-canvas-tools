package io.microservices.canvas.model

data class Events(val publishedEvents: MutableList<AggregateAndPublishedEvents>) {
}

data class AggregateAndPublishedEvents(val aggregateType : String, val eventTypes : MutableSet<String>) {

    fun add(eventType: String) {
        eventTypes.add(eventType);
    }

    constructor(aggregateType: String) : this(aggregateType, HashSet()) {

    }
}
