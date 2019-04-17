package io.microservices.canvas.extractor;

import io.microservices.canvas.builder.ServiceModelBuilder;

public interface ServiceModelExtractor {
  void extract(ServiceModelBuilder builder);
}
