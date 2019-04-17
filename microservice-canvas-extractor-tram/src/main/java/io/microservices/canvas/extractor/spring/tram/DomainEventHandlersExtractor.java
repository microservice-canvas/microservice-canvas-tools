package io.microservices.canvas.extractor.spring.tram;

import io.eventuate.tram.events.subscriber.DomainEventHandler;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.extractor.spring.AbstractSpringContextModelExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.List;

@Component
public class DomainEventHandlersExtractor extends AbstractSpringContextModelExtractor {

  @Override
  public void extract(ServiceModelBuilder builder) {
    for (String beanName : applicationContext.getBeanDefinitionNames()) {
      buildFromDomainEventHandler(builder, applicationContext.getBean(beanName));
    }
  }

  private void buildFromDomainEventHandler(ServiceModelBuilder builder, Object bean) {

    ReflectionUtils.doWithMethods(bean.getClass(), method -> {
      DomainEventHandlers handlers = (DomainEventHandlers) ReflectionUtils.invokeMethod(method, bean);
      fromHandler(builder, handlers);
    }, method -> DomainEventHandlers.class.isAssignableFrom(method.getReturnType()));
  }

  private void fromHandler(ServiceModelBuilder builder, DomainEventHandlers handlers) {
    List<DomainEventHandler> hs = handlers.getHandlers();
    hs.forEach(handler -> builder.addSubscribedToEvent(handler.getAggregateType(), handler.getEventClass().getName()));
  }
}
