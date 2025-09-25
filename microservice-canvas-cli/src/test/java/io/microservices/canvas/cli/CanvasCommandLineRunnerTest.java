package io.microservices.canvas.cli;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CanvasCommandLineRunnerTest {

  @Test
  public void shouldCreateAsciidoc() throws Exception {
    CanvasCommandLineRunner runner = new CanvasCommandLineRunner();
    File tmpFile = File.createTempFile("example", ".adoc");
    String resource = getClass().getResource("/example.yml").toExternalForm();
    assertTrue(resource.startsWith("file:"));
    String[] args = new String[]{"--input", resource.substring("file:".length()), "--output", tmpFile.getAbsolutePath()};
    runner.run(args);
    long length = tmpFile.length();
    assertTrue(length > 2000, "Should be long file: " + length);

    assertThat(length).isGreaterThan(200);
  }

}