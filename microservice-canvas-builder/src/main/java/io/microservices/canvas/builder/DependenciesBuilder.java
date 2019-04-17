package io.microservices.canvas.builder;

import io.microservices.canvas.model.Dependencies;
import io.microservices.canvas.model.InvokedChannelAndCommands;
import io.microservices.canvas.model.SubscribedToAggregateAndEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DependenciesBuilder {

  private Map<String, SubscribedToAggregateAndEvents> subscribedToEvents = new HashMap<>();
  private Map<String, InvokedChannelAndCommands> invokedOperations = new HashMap<>();

  public Dependencies build() {
    return new Dependencies(new ArrayList<>(invokedOperations.values()), new ArrayList<>(subscribedToEvents.values()));
  }

  public void addSubscribedToEvent(String aggregateType, String eventType) {
    subscribedToEvents.computeIfAbsent(aggregateType, k -> new SubscribedToAggregateAndEvents(aggregateType)).add(eventType);
  }

  public void addInvokedOperation(String commandChannel, String commandClass) {
    invokedOperations.computeIfAbsent(commandChannel, k -> new InvokedChannelAndCommands(commandChannel)).add(commandClass);
  }


}
