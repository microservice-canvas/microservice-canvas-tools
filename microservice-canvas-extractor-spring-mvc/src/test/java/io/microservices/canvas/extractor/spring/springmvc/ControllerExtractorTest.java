package io.microservices.canvas.extractor.spring.springmvc;

import io.microservices.canvas.builder.ServiceModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ControllerExtractorTest {

  private ControllerExtractor extractor;
  private ServiceModelBuilder builder;

  @BeforeEach
  public void setUp() {
    builder = mock(ServiceModelBuilder.class);
    extractor = new ControllerExtractor();
  }

  @Test
  public void shouldHandleSimpleCase() {
    class MyController1 {

      @GetMapping("/orders")
      public String getOrders() {
          return "hello";
      }

    }

    extractor.buildFromControllerClass(builder, MyController1.class);

    verify(builder).addSynchronousQueryEndpoint("getOrders", "GET", "/orders");
  }

  @Test
  public void shouldHandleComplexCase() {

    @RequestMapping(path="/orders")
    class MyController2 {

      @GetMapping
      public String getOrders() {
          return "hello";
      }

    }

    extractor.buildFromControllerClass(builder, MyController2.class);

    verify(builder).addSynchronousQueryEndpoint("getOrders", "GET", "/orders");
  }


}