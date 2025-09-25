package io.microservices.canvas.springmvc;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=CanvasSpringMvcTest.CanvasSpringMvcTestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CanvasSpringMvcTest {

  @Configuration
  @EnableAutoConfiguration
  @Import(MicroserviceCanvasWebConfiguration.class)
  public static class CanvasSpringMvcTestConfiguration {
  }

  @LocalServerPort
  private int port;

  @Test
  public void shouldGenerateAsciidoc() {
    ResponseEntity<String> r = new RestTemplate().getForEntity("http://localhost:%s/canvas.adoc".formatted(port), String.class);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    String asciidoc = r.getBody();
    System.out.println(asciidoc);
    assertThat(asciidoc, CoreMatchers.containsString("ftgo-order-service"));
  }

  @Test
  public void shouldGenerateJson() {
    ResponseEntity<String> r = new RestTemplate().getForEntity("http://localhost:%s/canvas.json".formatted(port), String.class);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    String json = r.getBody();
    System.out.println(json);
    assertThat(json, CoreMatchers.containsString("ftgo-order-service"));
  }

  @Test
  public void shouldGenerateYaml() {
    ResponseEntity<String> r = new RestTemplate().getForEntity("http://localhost:%s/canvas.yml".formatted(port), String.class);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    String yaml = r.getBody();
    System.out.println(yaml);
    assertThat(yaml, CoreMatchers.containsString("ftgo-order-service"));
  }

  @Test
  public void shouldGenerateHtml() {
    ResponseEntity<String> r = new RestTemplate().getForEntity("http://localhost:%s/canvas.html".formatted(port), String.class);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    String html = r.getBody();
    System.out.println(html);
    assertThat(html, CoreMatchers.containsString("ftgo-order-service"));
  }
}
