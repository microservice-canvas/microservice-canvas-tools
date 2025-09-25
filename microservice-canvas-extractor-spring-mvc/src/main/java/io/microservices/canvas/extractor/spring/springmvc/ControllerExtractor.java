package io.microservices.canvas.extractor.spring.springmvc;

import io.microservices.canvas.builder.ServiceModelBuilder;
import io.microservices.canvas.extractor.spring.AbstractSpringContextModelExtractor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

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
    RequestMapping rm = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
    String[] methodPaths = (rm.value() != null && rm.value().length > 0) ? rm.value() : rm.path();
    if (isCommand(rm))
      builder.addSynchronousCommandEndpoint(method.getName(), rm.method()[0].name(), resolvePath(basePath, methodPaths));
    if (isQuery(rm))
      builder.addSynchronousQueryEndpoint(method.getName(), rm.method()[0].name(), resolvePath(basePath, methodPaths));
  }

  private String basePath(Class<?> controllerClass) {
    RequestMapping classRm = AnnotationUtils.findAnnotation(controllerClass, RequestMapping.class);
    if (classRm == null) return "";
    String[] paths = (classRm.value() != null && classRm.value().length > 0) ? classRm.value() : classRm.path();
    return paths.length > 0 ? paths[0] : "";
  }

  private String resolvePath(String basePath, String[] path) {
    if (path == null || path.length == 0) {
      return basePath.isEmpty() ? "/" : basePath;
    }
    if (basePath.isEmpty())
      return path[0];
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