package io.microservices.canvas.cli;

public class MicroserviceCliMain {

  public static void main(String[] args) throws Exception {
    CanvasCommandLineRunner cli = new CanvasCommandLineRunner();
    cli.run(args);
  }
}
