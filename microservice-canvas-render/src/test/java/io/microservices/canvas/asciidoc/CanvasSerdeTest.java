package io.microservices.canvas.asciidoc;

import io.microservices.canvas.model.Service;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CanvasSerdeTest {

  @Test
  public void shouldSerializeToJson() {
    String json = ServiceToJSon.toJson(ServiceMother.service);
    System.out.println(json);
  }

  @Test
  public void shouldDeserializeFromJson() {
    String json = ServiceToJSon.toJson(ServiceMother.service);
    Service service = ServiceToJSon.fromJson(json, Service.class);
    assertEquals(ServiceMother.service, service);
  }

  @Test
  public void shouldSerializeToYaml() {
    String json = ServiceToJSon.toYaml(ServiceMother.service);
    System.out.println(json);
  }

  @Test
  public void shouldDeserializeFromYaml() {
    String yaml = ServiceToJSon.toYaml(ServiceMother.service);
    Service service = ServiceToJSon.fromYaml(yaml, Service.class);
    assertEquals(ServiceMother.service, service);
  }


  @Test
  public void shouldSerializeToHtml() throws IOException {
    String json = ServiceToJSon.toHtml(ServiceMother.service);
    System.out.println(json);
    FileUtils.write(new File("build/foo.html"), json, (String)null);
  }

}