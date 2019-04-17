package io.microservices.canvas.extractor.spring;

import io.microservices.canvas.extractor.ServiceModelHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = SpringContextExtractorConfiguration.class)
public class SpringContextExtractorConfiguration {

  @Bean
  public ServiceModelHolder serviceModelHolder() {
    return new ServiceModelHolder();
  }

  @Bean
  public SpringContextModelBuilder springContextModelExtractor() {
    return new SpringContextModelBuilder();
  }
}
