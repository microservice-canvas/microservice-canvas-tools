package io.microservices.canvas.extractor.spring.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceDescription {
  String description();
  String[] capabilities();
}
