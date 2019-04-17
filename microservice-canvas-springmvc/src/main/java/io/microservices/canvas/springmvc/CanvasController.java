package io.microservices.canvas.springmvc;

import io.microservices.canvas.asciidoc.ServiceToJSon;
import io.microservices.canvas.extractor.ServiceModelHolder;
import io.microservices.canvas.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.function.Function;

@Controller
public class CanvasController {

  @Autowired
  private ServiceModelHolder serviceModelHolder;

  @RequestMapping(path = "/canvas.adoc", method= RequestMethod.GET)
  public ResponseEntity<String> getAsciiDocCanvas() {
    return generateResponse(ServiceToJSon::toAsciidoc, "application/x-asciidoc");
  }

  private ResponseEntity<String> generateResponse(Function<Service, String> serializer, String contextType) {
    String json = serializer.apply(serviceModelHolder.getService());
    LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("content-type", contextType);
    return new ResponseEntity<>(json, headers, HttpStatus.OK);
  }

  @RequestMapping(path = "/canvas.json", method= RequestMethod.GET)
  public ResponseEntity<String> getServiceDefinitionAsJson() {
    return generateResponse(ServiceToJSon::toJson, "application/json");
  }

  @RequestMapping(path = "/canvas.yml", method= RequestMethod.GET)
  public ResponseEntity<String> getServiceDefinitionAsYaml() {
    return generateResponse(ServiceToJSon::toYaml, "application/x-yaml");
  }

  @RequestMapping(path = "/canvas.html", method= RequestMethod.GET)
  public ResponseEntity<String> getServiceDefinitionAsHtml() {
    return generateResponse(ServiceToJSon::toHtml, "text/html");
  }

}
