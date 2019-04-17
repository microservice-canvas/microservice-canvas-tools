package io.microservices.canvas.asciidoc

data class AggregateAndEvents(val aggregateType : String, val eventTypes : Set<String>)