package io.microservices.canvas.extractor.spring.tram;

import io.eventuate.tram.commands.consumer.CommandHandler;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.extractor.spring.AbstractSpringContextModelExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.List;

@Component
public class CommandHandlersExtractor extends AbstractSpringContextModelExtractor {

  @Override
  public void extract(ServiceModelBuilder builder) {
    for (String beanName : applicationContext.getBeanDefinitionNames()) {
      buildFromCommandHandler(builder, applicationContext.getBean(beanName));
    }
  }

  private void buildFromCommandHandler(ServiceModelBuilder builder, Object bean) {
    ReflectionUtils.doWithMethods(bean.getClass(), method -> {
      CommandHandlers handlers = (CommandHandlers) ReflectionUtils.invokeMethod(method, bean);
      fromCommandHandlers(builder, handlers);
    }, method -> CommandHandlers.class.isAssignableFrom(method.getReturnType()));
  }

  private void fromCommandHandlers(ServiceModelBuilder builder, CommandHandlers handlers) {
    List<CommandHandler> hs = handlers.getHandlers();
    hs.forEach(handler -> builder.addAsyncCommand(handler.getChannel(), handler.getCommandClass().getName()));
  }

}
