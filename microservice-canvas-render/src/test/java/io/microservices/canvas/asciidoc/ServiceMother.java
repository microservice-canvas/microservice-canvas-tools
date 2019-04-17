package io.microservices.canvas.asciidoc;

import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.model.Service;

public class ServiceMother {

  public static final Service service =
          ServiceModelBuilder.builder()
                  .withName("ftgo-order-service")
                  .withDescription("myDescription")
                  .withCapabilities(new String[]{"Order Management"})
                  // API
                  .addSynchronousCommandEndpoint("createOrder", "POST", "/orders")
                  .addSynchronousQueryEndpoint("findOrder", "GET", "/orders/{orderId}")
                  .addAsyncCommand("orderServiceChannel", "approveOrder")
                  .addPublishedEvent("orderChannel", "OrderCreatedEvent")
                  // Dependencies
                  .addInvokedOperation("kitchenServiceChannel", "createTicket")
                  .addSubscribedToEvent("restaurantEvents", "RestaurantCreated")
                  .build();

}
