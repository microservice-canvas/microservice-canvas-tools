package io.microservices.canvas.extractor.spring;

import io.microservices.canvas.extractor.ServiceModelExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class AbstractSpringContextModelExtractor implements ServiceModelExtractor {

  @Autowired
  protected ApplicationContext applicationContext;
}
