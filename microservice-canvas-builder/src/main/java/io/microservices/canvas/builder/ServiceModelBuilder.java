package io.microservices.canvas.builder;

import io.microservices.canvas.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ServiceModelBuilder {

  private String name = "unknown";
  private String description = "unknown";
  private List<String> capabilities = new LinkedList<>();

  private Map<String, Operation> commands = new HashMap<>();
  private Map<String, Operation> queries = new HashMap<>();

  // TODO map from channel to Set of eventTypes

  private Map<String, AggregateAndPublishedEvents> publishedEvents = new HashMap<>();


  private DependenciesBuilder dependenciesBuilder = new DependenciesBuilder();

  public static ServiceModelBuilder builder() {
    return new ServiceModelBuilder();

  }
  public Service build() {
    return new Service(name, description, capabilities, makeApi(), dependenciesBuilder.build());
  }

  private Api makeApi() {
    return new Api(new Operations(new LinkedList<>(commands.values()), new LinkedList<>(queries.values())),
            new Events(new LinkedList<>(publishedEvents.values())));
  }

  public ServiceModelBuilder addSynchronousCommandEndpoint(String operationName, String method, String path) {
    getCommand(operationName).add(new SynchronousOperationEndpoint(method, path));
    return this;
  }

  @NotNull
  private Operation getCommand(String operationName) {
    return commands.computeIfAbsent(operationName, (name) -> new Operation(name, OperationType.COMMAND, new LinkedList<>()));
  }

  public ServiceModelBuilder addSynchronousQueryEndpoint(String operationName, String method, String path) {
    getQuery(operationName).add(new SynchronousOperationEndpoint(method, path));
    return this;
  }

  @NotNull
  private Operation getQuery(String operationName) {
    return queries.computeIfAbsent(operationName, (name) -> new Operation(name, OperationType.COMMAND, new LinkedList<>()));
  }

  public ServiceModelBuilder addSubscribedToEvent(String aggregateType, String eventType) {
    dependenciesBuilder.addSubscribedToEvent(aggregateType, eventType);
    return this;
  }

  public ServiceModelBuilder addInvokedOperation(String commandChannel, String commandClass) {
    dependenciesBuilder.addInvokedOperation(commandChannel, commandClass);
    return this;
  }

  public ServiceModelBuilder withName(String serviceName) {
    this.name = serviceName;
    return this;
  }

  public ServiceModelBuilder addPublishedEvent(String aggregateType, String eventType) {
    publishedEvents.computeIfAbsent(aggregateType, k -> new AggregateAndPublishedEvents(aggregateType)).add(eventType);
    return this;
  }

  public ServiceModelBuilder addAsyncCommand(String channel, String commandName) {
    getCommand(commandName).add(new AsynchronousOperationEndpoint(channel));
    return this;
  }

  public ServiceModelBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public ServiceModelBuilder withCapabilities(String[] capabilities) {
    this.capabilities.addAll(Arrays.asList(capabilities));
    return this;
  }
}
