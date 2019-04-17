package io.microservices.canvas.extractor.spring.tram.sagas;

import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.extractor.spring.AbstractSpringContextModelExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class SagaParticipantProxyExtractor extends AbstractSpringContextModelExtractor {

  @Override
  public void extract(ServiceModelBuilder builder) {
    for (String beanName : applicationContext.getBeanDefinitionNames()) {
      buildFromProxy(builder, applicationContext.getBean(beanName));
    }

  }

  private void buildFromProxy(ServiceModelBuilder builder, Object bean) {
    ReflectionUtils.doWithFields(bean.getClass(), field -> {
      CommandEndpoint ce = (CommandEndpoint) ReflectionUtils.getField(field, bean);
      buildFromProxy(builder, ce);
    }, field -> CommandEndpoint.class.isAssignableFrom(field.getType()));
  }

  private void buildFromProxy(ServiceModelBuilder builder, CommandEndpoint ce) {
    Class commandClass = ce.getCommandClass();
    builder.addInvokedOperation(ce.getCommandChannel(), commandClass.getName());
  }

}
