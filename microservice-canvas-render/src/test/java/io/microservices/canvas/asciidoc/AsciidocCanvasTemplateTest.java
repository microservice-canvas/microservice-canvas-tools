package io.microservices.canvas.asciidoc;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsciidocCanvasTemplateTest {

  @Test
  public void shouldRenderTemplate() {
    String ad = ServiceToJSon.toAsciidoc(ServiceMother.service);
    System.out.println(ad);
    assertThat(ad, containsString("ftgo-order-service"));

    assertEquals(readExpectedCanvas(), ad);


  }

  private String readExpectedCanvas() {
    try (InputStream resourceAsStream = getClass().getResourceAsStream("/expected-ftgo-order-service-canvas.adoc")) {
      return IOUtils.toString(resourceAsStream, (String) null);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
