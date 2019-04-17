package io.microservices.canvas.model;

data class Operation(val name : String, val type : OperationType, val endpoints : MutableList<OperationEndpoint>) {
    fun add(endpoint: OperationEndpoint) {
        endpoints.add(endpoint)
    }
}

enum class OperationType { COMMAND, QUERY };

interface OperationEndpoint {
    val type : OperationEndpointType
}

enum class OperationEndpointType {
    SYNCHRONOUS, ASYNCHRONOUS
}

data class SynchronousOperationEndpoint(val method : String, val path : String) : OperationEndpoint {

    override val type = OperationEndpointType.SYNCHRONOUS
}

data class AsynchronousOperationEndpoint(val channel : String) : OperationEndpoint {

    override val type = OperationEndpointType.ASYNCHRONOUS
}


