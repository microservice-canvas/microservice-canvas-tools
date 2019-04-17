package io.microservices.canvas.asciidoc;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.microservices.canvas.model.AsynchronousOperationEndpoint;
import io.microservices.canvas.model.SynchronousOperationEndpoint;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = SynchronousOperationEndpoint.class,
                        name= "SYNCHRONOUS"),
                @JsonSubTypes.Type(value = AsynchronousOperationEndpoint.class,
                        name= "ASYNCHRONOUS")
        }
)
interface OperationEndpointMixin {
}
