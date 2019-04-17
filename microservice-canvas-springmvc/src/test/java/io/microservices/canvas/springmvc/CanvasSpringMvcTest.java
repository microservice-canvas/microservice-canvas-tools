package io.microservices.canvas.springmvc;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
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
    ResponseEntity<String> r = new RestTemplate().getForEntity(String.format("http://localhost:%s/canvas.adoc", port), String.class);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    String asciidoc = r.getBody();
    System.out.println(asciidoc);
    assertThat(asciidoc, CoreMatchers.containsString("ftgo-order-service"));
  }

  @Test
  public void shouldGenerateJson() {
    ResponseEntity<String> r = new RestTemplate().getForEntity(String.format("http://localhost:%s/canvas.json", port), String.class);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    String json = r.getBody();
    System.out.println(json);
    assertThat(json, CoreMatchers.containsString("ftgo-order-service"));
  }

  @Test
  public void shouldGenerateYaml() {
    ResponseEntity<String> r = new RestTemplate().getForEntity(String.format("http://localhost:%s/canvas.yml", port), String.class);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    String yaml = r.getBody();
    System.out.println(yaml);
    assertThat(yaml, CoreMatchers.containsString("ftgo-order-service"));
  }

  @Test
  public void shouldGenerateHtml() {
    ResponseEntity<String> r = new RestTemplate().getForEntity(String.format("http://localhost:%s/canvas.html", port), String.class);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    String html = r.getBody();
    System.out.println(html);
    assertThat(html, CoreMatchers.containsString("ftgo-order-service"));
  }
}
