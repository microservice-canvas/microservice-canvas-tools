package io.microservices.canvas.cli;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.*;

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
    assertTrue("Should be long file: " + length, length > 2000);

    assertThat(length).isGreaterThan(200);
  }

}