package io.microservices.canvas.cli;

import io.microservices.canvas.asciidoc.ServiceToJSon;
import io.microservices.canvas.model.Service;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

import static io.microservices.canvas.asciidoc.ServiceToJSon.fromYaml;

@Component
public class CanvasCommandLineRunner implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {


    CommandLine cmd = parseCommandLine(args);

    String inputFile = cmd.getOptionValue("input");
    String outputFile = cmd.getOptionValue("output");

    String yml = FileUtils.readFileToString(new File(inputFile), (String)null);
    Service service = fromYaml(yml, Service.class);
    String asciidoc = ServiceToJSon.toAsciidoc(service);
    FileUtils.write(new File(outputFile), asciidoc, (String) null);
  }

  private CommandLine parseCommandLine(String[] args) throws ParseException {
    Option inputFile = Option.builder().argName("input").longOpt("input").required().hasArg().build();
    Option outputFile = Option.builder().argName("input").longOpt("output").required().hasArg().build();

    Options options = new Options();
    options.addOption(inputFile);
    options.addOption(outputFile);
    CommandLineParser parser = new DefaultParser();
    return parser.parse( options, args);
  }

}
