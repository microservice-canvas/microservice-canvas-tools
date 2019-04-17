package io.microservices.canvas.extractor.spring.springmvc;

import io.microservices.canvas.builder.ServiceModelBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ControllerExtractorTest {

  private ControllerExtractor extractor;
  private ServiceModelBuilder builder;

  @Before
  public void setUp() {
    builder = mock(ServiceModelBuilder.class);
    extractor = new ControllerExtractor();
  }

  @Test
  public void shouldHandleSimpleCase() {
    class MyController1 {

      @RequestMapping(method= RequestMethod.GET, path="/orders")
      public void getOrders() {

      }

    }

    extractor.buildFromControllerClass(builder, MyController1.class);

    verify(builder).addSynchronousQueryEndpoint("getOrders", "GET", "/orders");
  }

  @Test
  public void shouldHandleComplexCase() {

    @RequestMapping(path="/orders")
    class MyController2 {

      @RequestMapping(method= RequestMethod.GET)
      public void getOrders() {

      }

    }

    extractor.buildFromControllerClass(builder, MyController2.class);

    verify(builder).addSynchronousQueryEndpoint("getOrders", "GET", "/orders");
  }


}