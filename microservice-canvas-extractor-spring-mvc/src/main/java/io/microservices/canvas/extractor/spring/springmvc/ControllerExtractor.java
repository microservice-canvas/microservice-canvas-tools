package io.microservices.canvas.extractor.spring.springmvc;

import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.extractor.spring.AbstractSpringContextModelExtractor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class ControllerExtractor extends AbstractSpringContextModelExtractor {

  @Override
  public void extract(ServiceModelBuilder builder) {

    Map<String, Object> controllerBeans = applicationContext.getBeansWithAnnotation(Controller.class);
    controllerBeans.forEach((name, controller) -> {
      Class<?> controllerClass = controller.getClass();
      buildFromControllerClass(builder, controllerClass);
    });
  }

  void buildFromControllerClass(ServiceModelBuilder builder, Class<?> controllerClass) {
    ReflectionUtils.doWithMethods(controllerClass, method -> {
      buildFromControllerMethod(builder, controllerClass, method);

    }, method -> AnnotationUtils.findAnnotation(method, RequestMapping.class) != null);
  }

  void buildFromControllerMethod(ServiceModelBuilder builder, Class<?> controllerClass, Method method) {
    String basePath = basePath(controllerClass);
    RequestMapping rm = AnnotationUtils.findAnnotation(method, RequestMapping.class);
    if (isCommand(rm))
      builder.addSynchronousCommandEndpoint(method.getName(), rm.method()[0].name(), resolvePath(basePath, rm.path()));
    if (isQuery(rm))
      builder.addSynchronousQueryEndpoint(method.getName(), rm.method()[0].name(), resolvePath(basePath, rm.path()));
  }

  private String basePath(Class<?> controllerClass) {
    RequestMapping classRm = AnnotationUtils.findAnnotation(controllerClass, RequestMapping.class);
    return classRm != null && classRm.path().length > 0 ? classRm.path()[0] : "";
  }

  private String resolvePath(String basePath, String[] path) {
    if (basePath.isEmpty())
      return path[0];
    if (path.length == 0)
      return basePath;
    String p = path[0];
    return basePath + (p.endsWith("/") || p.startsWith("/") ? "" : "/") + p;
  }


  private boolean isCommand(RequestMapping rm) {
    return Stream.of(RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE).anyMatch(m -> Arrays.stream(rm.method()).anyMatch(m::equals));
  }

  private boolean isQuery(RequestMapping rm) {
    return Stream.of(RequestMethod.GET, RequestMethod.HEAD).anyMatch(m -> Arrays.stream(rm.method()).anyMatch(m::equals));
  }

}