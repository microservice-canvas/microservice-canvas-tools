package io.microservices.canvas.extractor.spring;

import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.extractor.ServiceModelExtractor;
import io.microservices.canvas.extractor.ServiceModelHolder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

public class SpringContextModelBuilder {

  @Autowired
  private ServiceModelHolder serviceModelHolder;

  @Autowired
  private ServiceModelExtractor[] extractors;

  @PostConstruct
  public void buildModel() {
    ServiceModelBuilder builder = ServiceModelBuilder.builder();
    Stream.of(extractors).forEach(extractor -> extractor.extract(builder));
    serviceModelHolder.setService(builder.build());
  }





}
