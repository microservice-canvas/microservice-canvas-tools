package io.microservices.canvas.extractor.spring;

import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.extractor.spring.annotations.ServiceDescription;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DescriptionAndCapabilityExtractor extends AbstractSpringContextModelExtractor {
  @Override
  public void extract(ServiceModelBuilder builder) {
    Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ServiceDescription.class);
    beans.forEach((name, bean) -> {
      ServiceDescription a = bean.getClass().getDeclaredAnnotation(ServiceDescription.class);
      builder.withDescription(a.description()).withCapabilities(a.capabilities());
    });
  }
}
