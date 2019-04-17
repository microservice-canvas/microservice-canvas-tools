package io.microservices.canvas.extractor.spring.tram;

import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.extractor.spring.AbstractSpringContextModelExtractor;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Component
public class DomainEventPublisherExtractor extends AbstractSpringContextModelExtractor {

  @Override
  public void extract(ServiceModelBuilder builder) {
    applicationContext.getBeansOfType(AbstractAggregateDomainEventPublisher.class).values().forEach(dep -> buildFromDomainEventPublisher(builder, dep));
  }

  private void buildFromDomainEventPublisher(ServiceModelBuilder builder, AbstractAggregateDomainEventPublisher dep) {
    Type iface = dep.getClass().getGenericSuperclass();
    Type p = ((ParameterizedType) iface).getActualTypeArguments()[1];
    Class aggregateType = dep.getAggregateType();
    builder.addPublishedEvent(aggregateType.getName(), p.getTypeName());
  }


}
