package io.microservices.canvas.asciidoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.microservices.canvas.model.Service;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CanvasSerdeTest {

  @Test
  public void shouldSerializeToJson() throws JsonProcessingException {
    String json = ServiceToJSon.toJson(ServiceMother.service);
    System.out.println(json);
  }

  @Test
  public void shouldDeserializeFromJson() throws JsonProcessingException {
    String json = ServiceToJSon.toJson(ServiceMother.service);
    Service service = ServiceToJSon.fromJson(json, Service.class);
    assertEquals(ServiceMother.service, service);
  }

  @Test
  public void shouldSerializeToYaml() throws JsonProcessingException {
    String json = ServiceToJSon.toYaml(ServiceMother.service);
    System.out.println(json);
  }

  @Test
  public void shouldSerializeToHtml() throws IOException {
    String json = ServiceToJSon.toHtml(ServiceMother.service);
    System.out.println(json);
    FileUtils.write(new File("build/foo.html"), json, (String)null);
  }

}