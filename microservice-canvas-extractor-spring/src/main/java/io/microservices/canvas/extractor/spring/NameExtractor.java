package io.microservices.canvas.extractor.spring;

import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.extractor.ServiceModelExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NameExtractor implements ServiceModelExtractor {

  @Value("${spring.application.name}")
  private String serviceName;


  @Override
  public void extract(ServiceModelBuilder builder) {
    builder.withName(serviceName);
  }
}
