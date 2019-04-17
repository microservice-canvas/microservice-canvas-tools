package io.microservices.canvas.asciidoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import io.microservices.canvas.model.OperationEndpoint;
import io.microservices.canvas.model.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;

public class ServiceToJSon {

  public static <T> String toJson(T service) {
    ObjectMapper om = new ObjectMapper().registerModule(new KotlinModule());
    try {
      return om.writeValueAsString(service);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static String toYaml(Service service) {
    ObjectMapper om = new ObjectMapper(new YAMLFactory()).registerModule(new KotlinModule());
    try {
      return om.writeValueAsString(service);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(String json, Class<T> targetType) {
    ObjectMapper om = new ObjectMapper()
            .addMixIn(OperationEndpoint.class, OperationEndpointMixin.class)
            .registerModule(new KotlinModule())
            ;
    try {
      return om.readValue(json, targetType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String toAsciidoc(Service service) {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setTemplateMode(TemplateMode.TEXT);
    templateResolver.setSuffix(".adoc");

    return processTemplate(service, templateResolver);
  }

  private static String processTemplate(Service service, ClassLoaderTemplateResolver templateResolver) {
    templateResolver.setPrefix("/templates/");
    templateResolver.setCacheable(true);
    TemplateEngine te = new SpringTemplateEngine();
    te.setTemplateResolver(templateResolver);
    Context context = new Context();
    context.setVariable("service", new Canvas(service));
    return te.process("/microservicecanvas/asciiDocCanvas", context);
  }

  public static String toHtml(Service service) {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setSuffix(".html");

    return processTemplate(service, templateResolver);
  }

}
